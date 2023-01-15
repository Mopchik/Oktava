package com.uxapp.oktava.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxapp.oktava.utils.Genre

@Entity(tableName = "songs_learning")
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name:String,
    val album:String,
    val author:String,
    val genres:List<Genre>,
    //val allSteps:List<Int>,
    //val doneSteps:List<Int>,
    //val stepCount:Int,
    var lastStep:Int,
    val filePicture:String,
    var recordingsCount:Int,
    var fileWords:String? = null,
    var fileNotesForGuitar:String? = null,
    var fileNotesForPiano:String? = null)