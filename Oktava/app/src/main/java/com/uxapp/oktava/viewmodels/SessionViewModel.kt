package com.uxapp.oktava.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.SessionCompleted
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository
import com.uxapp.oktava.storage.repositories.SongsRepository
import com.uxapp.oktava.utils.MyTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SessionViewModel(
    private val recordingsRep: RecordingsRepository,
    private val songsRep: SongsRepository,
    private val sessionsRep: SessionsCompletedRepository
): ViewModel() {
    private lateinit var sessionStartDate: Calendar
    private var sessionId: Long = -1
    lateinit var sessionLiveData: LiveData<SessionCompleted>

    fun startSession(){
        sessionStartDate = Calendar.getInstance()
        sessionId = sessionStartDate.timeInMillis
        val newSession = SessionCompleted(
            sessionStartDate.timeInMillis,
            ArrayList<String>(),
            sessionStartDate,
            MyTime(-1, -1, -1)
        )
        sessionsRep.addNewItem(newSession)
        sessionLiveData = sessionsRep.getSessionByIdAsLiveData(sessionId)
    }

    fun addNewRecording(recording: Recording){
        viewModelScope.launch(Dispatchers.IO) {
            val song = songsRep.getSongById(recording.idOfSong)
            if(recording.step > song.lastStep){
                song.lastStep = recording.step
            }
            song.recordingsCount++
            songsRep.changeItem(song)

            recordingsRep.addNewItem(recording)
            val session = sessionsRep.getSessionById(id = sessionId)
            session.recordingsID.add(recording.path)
            sessionsRep.changeItem(session)
        }
    }

    fun deleteRecording(item: Recording) {
        viewModelScope.launch(Dispatchers.IO) {
            val session = sessionsRep.getSessionById(id = sessionId)
            session.recordingsID.remove(item.path)
            sessionsRep.changeItem(session)
        }
        recordingsRep.deleteItem(item)
    }

    fun endSession(step: Int){
        val sessionEndDate = Calendar.getInstance()
        viewModelScope.launch(Dispatchers.IO) {
            val session = sessionsRep.getSessionById(id = sessionId)
            session.duration = MyTime.from(
                sessionEndDate.timeInMillis - sessionStartDate.timeInMillis
            )
            sessionsRep.changeItem(session)
        }
    }
}