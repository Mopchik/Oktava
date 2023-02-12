package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.uxapp.oktava.storage.interactors.SongsInteractor
import com.uxapp.oktava.storage.mappers.SongsMapper
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.storage.repositories.SongsRepository
import com.uxapp.oktava.viewmodels.dataModels.SongCardModel
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel
import com.uxapp.oktava.viewmodels.songs.SongChooseProcess
import com.uxapp.oktava.viewmodels.songs.SongsCreationProcess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SongsViewModel(
    private val songsRepository: SongsRepository,
    private val songsInteractor: SongsInteractor
): ViewModel() {
    val songsCreationProcess = SongsCreationProcess(
        doOnFinish = this::addOrEditSong
    )
    val songsChooseProcess = SongChooseProcess()

    fun getCompletedSongsFlow(): Flow<List<SongInListModel>>{
        return songsInteractor.getCompletedSongsFlow().map{ list ->
            list.map(SongsMapper::mapSongToSongInListModel)
        }
    }

    fun getInProgressSongsFlow(): Flow<List<SongInListModel>>{
        return songsInteractor.getInProgressSongsFlow().map{ list ->
            list.map(SongsMapper::mapSongToSongInListModel)
        }
    }

    fun getNotStartedSongsFlow(): Flow<List<SongInListModel>>{
        return songsInteractor.getNotStartedSongsFlow().map{ list ->
            list.map(SongsMapper::mapSongToSongInListModel)
        }
    }

    suspend fun getCardModelOfSong(idOfSong: Int): SongCardModel? {
        return songsInteractor.getCardModelOfSong(idOfSong)
    }

    suspend fun getSongById(idOfSong: Int): Song? {
        return songsRepository.getSongById(idOfSong)
    }

    suspend fun startCreationOrEdition(){
        songsCreationProcess.start()
        if(songsChooseProcess.isChosen()){
            val chosenSongId = songsChooseProcess.getChosenSongId()
            val song = getSongById(chosenSongId)
            if(song != null) songsCreationProcess.setSong(song)
        }
    }

    fun addOrEditSong(song: Song) {
        songsInteractor.addOrEditSong(song)
    }

    fun deleteSongById(id: Int){
        songsRepository.deleteItemById(id)
    }
}