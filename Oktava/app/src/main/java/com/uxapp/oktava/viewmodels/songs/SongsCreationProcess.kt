package com.uxapp.oktava.viewmodels.songs

import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.ui.main.models.SongAboutModel
import com.uxapp.oktava.utils.Genre

class SongsCreationProcess(
    private val doOnFinish: (s: Song) -> Unit
) {

    private var createdSong = Song.empty()

    fun start() {
        createdSong = Song.empty()
    }

    fun setSong(song: Song){
        createdSong = song
    }

    fun setName(name: String) {
        createdSong = createdSong.copy(
            name = name
        )
    }

    fun setAuthor(author: String) {
        createdSong = createdSong.copy(
            author = author
        )
    }

    fun setAlbum(album: String) {
        createdSong = createdSong.copy(
            album = album
        )
    }

    fun setYear(year: String) {
        createdSong = createdSong.copy(
            year = year
        )
    }

    fun setGenres(genres: List<Genre>) {
        createdSong = createdSong.copy(
            genres = genres
        )
    }

    fun setFilePicture(filePicture: String?) {
        if(filePicture == null) return
        createdSong = createdSong.copy(
            filePicture = filePicture
        )
    }

    fun setFileNotes(fileNotes: String?) {
        if(fileNotes == null) return
        createdSong = createdSong.copy(
            fileNotesForPiano = fileNotes
        )
    }

    fun setWords(words: String) {
        createdSong = createdSong.copy(
            words = words
        )
    }

    fun getSongAbout(): SongAboutModel {
        return SongAboutModel(
            name = createdSong.name,
            author = createdSong.author,
            album = createdSong.album,
            year = createdSong.year,
            genres = createdSong.genres,
            filePicture = createdSong.filePicture
        )
    }

    fun getSongWords(): String? {
        return createdSong.words
    }

    fun getSongFileNotes(): String? {
        return createdSong.fileNotesForPiano
    }

    fun finish() {
        doOnFinish(createdSong)
    }
}