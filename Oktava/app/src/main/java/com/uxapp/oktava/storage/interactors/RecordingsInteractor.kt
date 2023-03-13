package com.uxapp.oktava.storage.interactors

import androidx.lifecycle.viewModelScope
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository
import com.uxapp.oktava.storage.repositories.SongsRepository
import com.uxapp.oktava.utils.Period
import com.uxapp.oktava.utils.getPeriodOfDate
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel as SongWithRecordingsModel


class RecordingsInteractor @Inject constructor(
    private val recordingsRepository: RecordingsRepository,
    private val songsRepository: SongsRepository,
    private val sessionsRepository: SessionsCompletedRepository
) {

    fun getAllRecordingModelsFlow(): Flow<List<RecordingModel>> {
        return recordingsRepository.recordingsFlow.map { list ->
            list.mapNotNull {
                mapRecordingToRecordingModel(it)
            }
        }
    }

    suspend fun deleteRecording(path: String) {
        val item = recordingsRepository.getRecordingByPath(path)
        if(item != null) {
            recordingsRepository.deleteItem(item)
        }
        val sessions = sessionsRepository.getAllSessions()
        sessions.forEach { session ->
            if(session.recordingsID.remove(path)){
                sessionsRepository.changeItem(session)
            }
        }
    }

    fun getRecordingsCountFlow(): Flow<Int> {
        return recordingsRepository.recordingsFlow.map { it.size }
    }

    fun getWeeksCountFlow(): Flow<Long> {
        return recordingsRepository.recordingsFlow
            .map(this::calculateWeeksCountFromRecordingsList)
    }

    private fun calculateWeeksCountFromRecordingsList(recordingsList: List<Recording>): Long {
        var minDate = Calendar.getInstance()
        recordingsList.forEach {
            val tempDate = it.date
            if (tempDate < minDate) minDate = tempDate
        }
        return calculateWeeksBetweenTwoDates(minDate, Calendar.getInstance())
    }

    private fun calculateWeeksBetweenTwoDates(first: Calendar, second: Calendar): Long {
        val difMillis = second.timeInMillis - first.timeInMillis
        val hours = difMillis / 1000 / 60 / 60
        return hours / 24 / 7
    }

    suspend fun mapRecordingToRecordingModel(recording: Recording?): RecordingModel? {
        if(recording == null) return null
        val recordingSong = songsRepository.getSongById(recording.idOfSong) ?: return null
        return RecordingModel(
            name = recording.path,
            path = recording.path,
            description = recording.description,
            step = recording.step,
            date = recording.date,
            duration = recording.duration,
            filePicture = recordingSong.filePicture
        )
    }

    fun getPeriodRecordingsFlow(period: Period): Flow<List<RecordingModel>> {
        return recordingsRepository.recordingsFlow.map { list ->
            list.mapNotNull {
                if(it.date.getPeriodOfDate() == period){
                    mapRecordingToRecordingModel(it)
                } else {
                    null
                }
            }
        }
    }
}