package com.uxapp.oktava.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxapp.oktava.utils.Genre

@Entity(tableName = "songs_learning")
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val album: String,
    val author: String,
    val genres: List<Genre>,
    val year: String,
    var lastStep: Int,
    val filePicture: String?,
    var recordingsCount: Int,
    val checkedTodos: List<String>,
    var words: String? = null,
    var fileNotesForGuitar: String? = null,
    var fileNotesForPiano: String? = null
) {
    companion object {
        fun empty(): Song {
            return Song(
                0, "", "", "", ArrayList(), "",
                0, null, 0, ArrayList()
            )
        }
    }
}