package com.uxapp.oktava.viewmodels.songs

import com.uxapp.oktava.storage.mappers.SongsMapper
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.ui.main.models.SongAboutModel
import com.uxapp.oktava.utils.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SongsCreationProcess(
    private val doOnFinish: (s: Song) -> Unit
) {

    private var createdSong = Song.empty()
    private val songAboutModelMutableStateFlow =
        MutableStateFlow(SongsMapper.mapSongToSongAboutModel(createdSong))
    private val songWordsMutableStateFlow = MutableStateFlow(createdSong.words)
    private val songFileNotesMutableStateFlow = MutableStateFlow(createdSong.fileNotesForPiano)

    fun start() {
        createdSong = Song.empty()
    }

    fun setSong(song: Song) {
        createdSong = song
        notifySongChanged()
    }

    fun setName(name: String) {
        createdSong = createdSong.copy(
            name = name
        )
        notifySongChanged()
    }

    fun setAuthor(author: String) {
        createdSong = createdSong.copy(
            author = author
        )
        notifySongChanged()
    }

    fun setAlbum(album: String) {
        createdSong = createdSong.copy(
            album = album
        )
        notifySongChanged()
    }

    fun setYear(year: String) {
        createdSong = createdSong.copy(
            year = year
        )
        notifySongChanged()
    }

    fun setGenres(genres: List<Genre>) {
        createdSong = createdSong.copy(
            genres = genres
        )
        notifySongChanged()
    }

    fun setFilePicture(filePicture: String?) {
        if (filePicture == null) return
        createdSong = createdSong.copy(
            filePicture = filePicture
        )
        notifySongChanged()
    }

    fun setFileNotes(fileNotes: String?) {
        if (fileNotes == null) return
        createdSong = createdSong.copy(
            fileNotesForPiano = fileNotes
        )
        songFileNotesMutableStateFlow.value = fileNotes
    }

    fun setWords(words: String) {
        createdSong = createdSong.copy(
            words = words
        )
        songWordsMutableStateFlow.value = words
    }

    fun getSongAboutFlow(): Flow<SongAboutModel> {
        return songAboutModelMutableStateFlow
    }

    fun getSongWordsFlow(): Flow<String?> {
        return songWordsMutableStateFlow
    }

    fun getSongFileNotesFlow(): Flow<String?> {
        return songFileNotesMutableStateFlow
    }

    fun finish() {
        doOnFinish(createdSong)
    }

    private fun notifySongChanged() {
        songAboutModelMutableStateFlow.value = SongsMapper.mapSongToSongAboutModel(createdSong)
        songWordsMutableStateFlow.value = createdSong.words
        songFileNotesMutableStateFlow.value = createdSong.fileNotesForPiano
    }
}