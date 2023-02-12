package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.RecordingDao
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordingsRepository @Inject constructor(
    private val recordingDao: RecordingDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    val recordingsFlow = recordingDao.getFlow()

    fun addNewItem(item: Recording){
        applicationScope.launch(Dispatchers.IO) {
            recordingDao.addRecording(item)
        }
    }

    fun deleteItem(item: Recording) {
        applicationScope.launch(Dispatchers.IO) {
            recordingDao.removeRecording(item)
        }
    }

    fun deleteItemByPath(path: String) {
        applicationScope.launch(Dispatchers.IO) {
            recordingDao.removeRecordingByPath(path)
        }
    }

    fun changeItem(newItem: Recording){
        applicationScope.launch(Dispatchers.IO) {
           recordingDao.editRecording(newItem)
        }
    }

    fun getRecordingsOfSongFlow(idOfSong: Int): Flow<List<Recording>> {
        return recordingDao.getRecordingsOfSongFlow(idOfSong)
    }

    suspend fun getRecordingByPath(path: String) = recordingDao.getRecordingByPath(path)
}