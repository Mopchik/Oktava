package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.SongsLearningDao
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SongsRepository @Inject constructor(
    private val songsLearningDao: SongsLearningDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    val allSongsFlow = songsLearningDao.getFlow()

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

    fun deleteItemById(id: Int) {
        applicationScope.launch(Dispatchers.IO) {
            songsLearningDao.removeSongById(id)
        }
    }

    fun changeItem(newItem: Song?) {
        if(newItem == null) return
        applicationScope.launch(Dispatchers.IO) {
            songsLearningDao.editSong(newItem)
        }
    }

    suspend fun getSongById(id: Int): Song? {
        val listResult = songsLearningDao.getSongById(id)
        return if(listResult.isNotEmpty()) listResult.first() else null
    }

    fun getSongByIdFlow(id: Int): Flow<Song?> {
        return songsLearningDao.getSongByIdFlow(id).map{
            if(it.isNotEmpty()) it.first() else null
        }
    }
}