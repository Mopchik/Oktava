package com.uxapp.oktava.viewmodels.dataModels

import java.time.Duration
import java.util.*

data class SessionsOfDayModel(
    val day: Calendar,
    val wholeDuration: Duration,
    val recordings: List<RecordingModel>,
    val stepsCount: Int,
    val songsCount: Int
)