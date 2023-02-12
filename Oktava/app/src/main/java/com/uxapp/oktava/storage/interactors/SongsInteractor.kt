package com.uxapp.oktava.storage.interactors

import com.uxapp.oktava.storage.mappers.SongsMapper
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.storage.repositories.SongsRepository
import com.uxapp.oktava.utils.AppScope
import com.uxapp.oktava.utils.CoroutineScopeQualifier
import com.uxapp.oktava.utils.Layer
import com.uxapp.oktava.utils.MAX_STEP
import com.uxapp.oktava.viewmodels.dataModels.SongCardModel
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class SongsInteractor @Inject constructor(
    private val songsRepository: SongsRepository,
    @CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    fun addOrEditSong(song: Song){
        applicationScope.launch(Dispatchers.IO) {
            val songFromDatabase = songsRepository.getSongById(song.id)
            if (songFromDatabase == null) {
                songsRepository.addNewItem(song)
            } else {
                songsRepository.changeItem(song)
            }
        }
    }

    fun getCompletedSongsFlow(): Flow<List<Song>> {
        return songsRepository.allSongsFlow.map{ list ->
            list.mapNotNull {
                if(it.lastStep >= MAX_STEP) it else null
            }
        }
    }

    fun getInProgressSongsFlow(): Flow<List<Song>> {
        return songsRepository.allSongsFlow.map{ list ->
            list.mapNotNull {
                if(it.lastStep in 1 until MAX_STEP) it else null
            }
        }

    }

    fun getNotStartedSongsFlow(): Flow<List<Song>> {
        return songsRepository.allSongsFlow.map{ list ->
            list.mapNotNull {
                if(it.lastStep <= 0) it else null
            }
        }
    }

    suspend fun getCardModelOfSong(idOfSong: Int): SongCardModel? {
        val song = songsRepository.getSongById(idOfSong) ?: return null
        return SongsMapper.mapSongToSongCardModel(song)
    }
}