package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxapp.oktava.storage.interactors.RecordingsInteractor
import com.uxapp.oktava.storage.interactors.SongsInteractor
import com.uxapp.oktava.storage.mappers.SongsMapper
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel
import com.uxapp.oktava.utils.Period
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecordingsViewModel(
    private val recordingsRepository: RecordingsRepository,
    private val recordingsInteractor: RecordingsInteractor,
    private val songsInteractor: SongsInteractor
) : ViewModel() {
    fun getRecordingsOfSongFlow(idOfSong: Int): Flow<List<RecordingModel>> {
        return recordingsRepository.getRecordingsOfSongFlow(idOfSong).map { list ->
            list.mapNotNull {
                recordingsInteractor.mapRecordingToRecordingModel(it)
            }
        }
    }

    fun getAllRecordingsFlow(): Flow<List<RecordingModel>> {
        return recordingsInteractor.getAllRecordingModelsFlow()
    }

    fun deleteRecording(path: String) {
        viewModelScope.launch {
            recordingsInteractor.deleteRecording(path)
        }
    }

    fun getCompletedSongsWithRecordingsFlow(): Flow<List<SongWithRecordingsModel>> {
        return songsInteractor.getCompletedSongsFlow().map { list ->
            list.map(SongsMapper::mapSongToSongWithRecordingModel)
        }
    }

    fun getInProgressSongsWithRecordingsFlow(): Flow<List<SongWithRecordingsModel>> {
        return songsInteractor.getInProgressSongsFlow().map { list ->
            list.map(SongsMapper::mapSongToSongWithRecordingModel)
        }
    }

    fun getNotStartedSongsWithRecordingsFlow(): Flow<List<SongWithRecordingsModel>> {
        return songsInteractor.getNotStartedSongsFlow().map { list ->
            list.map(SongsMapper::mapSongToSongWithRecordingModel)
        }
    }

    fun getRecordingsCountFlow(): Flow<Int> {
        return recordingsInteractor.getRecordingsCountFlow()
    }

    fun getWeeksCountFlow(): Flow<Long> {
        return recordingsInteractor.getWeeksCountFlow()
    }

    fun getTodayRecordingsFlow(): Flow<List<RecordingModel>> {
        return recordingsInteractor.getPeriodRecordingsFlow(Period.TODAY)
    }

    fun getYesterdayRecordingsFlow(): Flow<List<RecordingModel>> {
        return recordingsInteractor.getPeriodRecordingsFlow(Period.YESTERDAY)
    }

    fun getWeekRecordingsFlow(): Flow<List<RecordingModel>> {
        return recordingsInteractor.getPeriodRecordingsFlow(Period.THIS_WEEK)
    }

    fun getMonthRecordingsFlow(): Flow<List<RecordingModel>> {
        return recordingsInteractor.getPeriodRecordingsFlow(Period.THIS_MONTH)
    }

    fun getBeforeRecordingsFlow(): Flow<List<RecordingModel>> {
        return recordingsInteractor.getPeriodRecordingsFlow(Period.BEFORE)
    }
}