package com.uxapp.oktava.viewmodels.dataModels

import com.uxapp.oktava.utils.MyTime
import java.util.*

data class RecordingCreatedModel(
    val path: String,
    val description: String,
    val duration: MyTime,
)