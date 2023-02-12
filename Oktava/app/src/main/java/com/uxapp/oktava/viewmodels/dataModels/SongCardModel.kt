package com.uxapp.oktava.viewmodels.dataModels

data class SongCardModel(
    val id: Int,
    val name:String,
    val album:String,
    val author:String,
    val year: String,
    val genresAsString:String,
    val filePicture:String?,
    var words:String? = null,
    var fileNotesForGuitar:String? = null,
    var fileNotesForPiano:String? = null
)