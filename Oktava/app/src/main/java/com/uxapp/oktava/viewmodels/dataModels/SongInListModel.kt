package com.uxapp.oktava.viewmodels.dataModels

data class SongInListModel(
    val id: Int,
    val name:String,
    val author:String,
    var lastStep:Int,
    val filePicture: String?
)