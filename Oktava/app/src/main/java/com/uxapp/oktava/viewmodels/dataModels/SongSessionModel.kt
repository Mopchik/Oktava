package com.uxapp.oktava.viewmodels.dataModels

data class SongSessionModel(
    val id: Int,
    val name: String,
    val step: Int,
    val filePicture: String?,
    val fileNotes: String?,
    val words: String?,
)