package com.uxapp.oktava.ui.main.models

import com.uxapp.oktava.utils.Genre

data class SongAboutModel(
    val name: String,
    val author: String,
    val album: String,
    val genres: List<Genre>,
    val year: String,
    val filePicture: String?
)