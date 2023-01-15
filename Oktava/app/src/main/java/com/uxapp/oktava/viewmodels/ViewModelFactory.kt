package com.uxapp.oktava.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxapp.oktava.storage.repositories.*
import com.uxapp.oktava.utils.AppScope
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(
    private val recordingsRep: RecordingsRepository,
    private val songsRep: SongsRepository,
    private val sessionsRep: SessionsCompletedRepository,
    private val calendarTimesRep: CalendarTimesRepository,
    private val scheduleTimesRep: ScheduleTimesRepository,
    private val prefs: SharedPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        CalendarViewModel::class.java ->
            CalendarViewModel(sessionsRep, calendarTimesRep, scheduleTimesRep)
        RecordingsViewModel::class.java ->
            RecordingsViewModel(recordingsRep)
        SessionViewModel::class.java ->
            SessionViewModel(recordingsRep, songsRep, sessionsRep)
        SongsViewModel::class.java ->
            SongsViewModel(songsRep)
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}