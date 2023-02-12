package com.uxapp.oktava.storage.mappers

import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel
import com.uxapp.oktava.utils.Genre
import com.uxapp.oktava.utils.StringConverter.genreToString
import com.uxapp.oktava.viewmodels.dataModels.SongCardModel
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel

object SongsMapper {
    fun mapSongToSongInListModel(song: Song): SongInListModel {
        return SongInListModel(
            id = song.id,
            name = song.name,
            author = song.author,
            lastStep = song.lastStep,
            filePicture = song.filePicture,
        )
    }

    fun mapSongToSongCardModel(song: Song): SongCardModel {
        return SongCardModel(
            id = song.id,
            name = song.name,
            album = song.album,
            author = song.author,
            year = song.year,
            genresAsString = genresToString(song.genres),
            filePicture = song.filePicture,
            words = song.words,
            fileNotesForGuitar = song.fileNotesForGuitar,
            fileNotesForPiano = song.fileNotesForPiano
        )
    }

    private fun genresToString(genres: List<Genre>): String {
        if(genres.isEmpty()) return ""
        val stringBuilder = StringBuilder()
        for(genreInd in 0 until genres.size - 1){
            stringBuilder.append(genreToString(genres[genreInd]) + ", ")
        }
        stringBuilder.append(genreToString(genres.last()))
        return stringBuilder.toString()
    }

    fun mapSongToSongSessionModel(song: Song): SongSessionModel {
        return SongSessionModel(
            id = song.id,
            name = song.name,
            step = song.lastStep,
            filePicture = song.filePicture,
            fileNotes = song.fileNotesForPiano,
            words = song.words
        )
    }

    fun mapSongToSongWithRecordingModel(song: Song): SongWithRecordingsModel {
        return SongWithRecordingsModel(
            id = song.id,
            name = song.name,
            author = song.author,
            filePicture = song.filePicture,
            recordingsCount = song.recordingsCount
        )
    }
}