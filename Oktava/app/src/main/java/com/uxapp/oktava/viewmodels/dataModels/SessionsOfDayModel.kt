package com.uxapp.oktava.viewmodels.dataModels

import com.uxapp.oktava.utils.MyTime
import java.time.Duration
import java.util.*

data class SessionsOfDayModel(
    val day: Calendar,
    val wholeDuration: MyTime,
    val recordings: List<RecordingModel>,
    val stepsCount: Int,
    val songsCount: Int
)