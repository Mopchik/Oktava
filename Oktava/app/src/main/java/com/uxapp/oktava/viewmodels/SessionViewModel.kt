package com.uxapp.oktava.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxapp.oktava.storage.holders.RecordingNameHolder
import com.uxapp.oktava.storage.interactors.RecordingsInteractor
import com.uxapp.oktava.storage.mappers.SongsMapper
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.SessionCompleted
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository
import com.uxapp.oktava.storage.repositories.SongsRepository
import com.uxapp.oktava.utils.MyTime
import com.uxapp.oktava.viewmodels.dataModels.RecordingCreatedModel
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel
import com.uxapp.oktava.viewmodels.songs.SongChooseProcess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SessionViewModel(
    private val recordingsRep: RecordingsRepository,
    private val songsRep: SongsRepository,
    private val sessionsRep: SessionsCompletedRepository,
    private val recordingsInteractor: RecordingsInteractor
) : ViewModel() {
    private lateinit var sessionStartDate: Calendar
    private var sessionId: Long = -1

    private val recordingNameHolder by lazy {
        RecordingNameHolder()
    }

    val songsChooseProcess = SongChooseProcess()

    fun getSongSessionModelsFlow(): Flow<List<SongSessionModel>> {
        return songsRep.allSongsFlow.map { list ->
            list.map(SongsMapper::mapSongToSongSessionModel)
        }
    }

    fun startSession() {
        sessionStartDate = Calendar.getInstance()
        sessionId = sessionStartDate.timeInMillis
        val newSession = SessionCompleted(
            sessionStartDate.timeInMillis,
            ArrayList<String>(),
            sessionStartDate,
            MyTime(-1, -1, -1)
        )
        sessionsRep.addNewItem(newSession)
    }

    suspend fun getSessionModelOfSong(idOfSong: Int): SongSessionModel? {
        val song = songsRep.getSongById(idOfSong) ?: return null
        return SongsMapper.mapSongToSongSessionModel(song)
    }

    fun setStepChosen(step: Int) {
        viewModelScope.launch {
            val song = songsRep.getSongById(songsChooseProcess.getChosenSongId())
            songsRep.changeItem(song?.copy(lastStep = step))
        }
    }

    fun checkTodo(todo: String, isChecked: Boolean) {
        viewModelScope.launch (Dispatchers.IO) {
            val song = songsRep.getSongById(songsChooseProcess.getChosenSongId()) ?: return@launch
            val listTodos = song.checkedTodos.toMutableList()
            if (isChecked) {
                listTodos.add(todo)
            } else {
                listTodos.remove(todo)
            }
            songsRep.changeItem(song.copy(checkedTodos = listTodos))
        }
    }

    suspend fun getCheckedTodos(): List<String>? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            val song = songsRep.getSongById(songsChooseProcess.getChosenSongId())
            song?.checkedTodos
        }
    }

    suspend fun getNameForNewRecording(): String? {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            val song = songsRep.getSongById(songsChooseProcess.getChosenSongId()) ?: return@withContext null
            val recording = song.name + " | " + (song.recordingsCount + 1)
            recordingNameHolder.put(recording)
            recording
        }
    }

    fun getNameOfRecording(): String {
        return recordingNameHolder.get()
    }

    fun addNewRecording(recordingCreatedModel: RecordingCreatedModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val song = songsRep.getSongById(songsChooseProcess.getChosenSongId()) ?: return@launch
            song.recordingsCount++
            songsRep.changeItem(song)

            val recording = Recording(
                path = recordingCreatedModel.path,
                idOfSong = songsChooseProcess.getChosenSongId(),
                description = recordingCreatedModel.description,
                step = song.lastStep,
                date = Calendar.getInstance(),
                duration = recordingCreatedModel.duration
            )
            recordingsRep.addNewItem(recording)
            val session = sessionsRep.getSessionById(id = sessionId)
            session?.recordingsID?.add(recordingCreatedModel.path)
            sessionsRep.changeItem(session)
        }
    }

    fun getRecordingsOfThisSessionFlow(): Flow<List<RecordingModel>> {
        return sessionsRep.getRecordingsOfSessionFlow(sessionId).map { list ->
            Log.e("KOSTIK", "RecordingList: $list")
            list.mapNotNull {
                Log.e("KOSTIK", "Recording: $it")
                Log.e("KOSTIK", "RecordingModel: ${recordingsInteractor.mapRecordingToRecordingModel(it)}")
                recordingsInteractor.mapRecordingToRecordingModel(it)
            }
        }
    }

    fun deleteRecording(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val session = sessionsRep.getSessionById(id = sessionId)
            session?.recordingsID?.remove(path)
            sessionsRep.changeItem(session)
            val item = recordingsRep.getRecordingByPath(path) ?: return@launch
            recordingsRep.deleteItem(item)
        }
    }

    fun endSession() {
        val sessionEndDate = Calendar.getInstance()
        viewModelScope.launch(Dispatchers.IO) {
            val session = sessionsRep.getSessionById(id = sessionId)
            session?.duration = MyTime.from(
                sessionEndDate.timeInMillis - sessionStartDate.timeInMillis
            )
            sessionsRep.changeItem(session)
        }
    }
}