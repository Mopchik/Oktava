package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.SongsLearningDao
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SongsRepository @Inject constructor(
    private val songsLearningDao: SongsLearningDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    val liveData = songsLearningDao.getLiveData()

    fun addNewItem(item: Song){
        applicationScope.launch(Dispatchers.IO) {
            songsLearningDao.addSong(item)
        }
    }

    fun deleteItem(item: Song) {
        applicationScope.launch(Dispatchers.IO) {
            songsLearningDao.removeSong(item)
        }
    }

    fun changeItem(newItem: Song) {
        applicationScope.launch(Dispatchers.IO) {
            songsLearningDao.editSong(newItem)
        }
    }

    suspend fun getSongById(id: Int): Song{
        return songsLearningDao.getSongById(id)
    }
}