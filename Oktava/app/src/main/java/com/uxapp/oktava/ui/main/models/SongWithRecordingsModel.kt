package com.uxapp.oktava.ui.main.models

data class SongWithRecordingsModel(
    val id: Int,
    val name: String,
    val author: String,
    val filePicture: String?,
    var recordingsCount: Int,
)