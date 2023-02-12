package com.uxapp.oktava.viewmodels.dataModels

import com.uxapp.oktava.utils.MyTime
import java.util.*

data class RecordingModel(
    val name:String,
    val path: String,
    val description: String,
    val step: Int,
    val date: Calendar,
    val duration: MyTime,
    val filePicture:String?
)