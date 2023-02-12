package com.uxapp.oktava.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxapp.oktava.storage.interactors.CalendarInteractor
import com.uxapp.oktava.storage.interactors.RecordingsInteractor
import com.uxapp.oktava.storage.interactors.SongsInteractor
import com.uxapp.oktava.storage.repositories.*
import com.uxapp.oktava.utils.AppScope
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@AppScope
class ViewModelFactory @Inject constructor(
    private val recordingsRep: RecordingsRepository,
    private val songsRep: SongsRepository,
    private val sessionsRep: SessionsCompletedRepository,
    private val calendarTimesRep: CalendarTimesRepository,
    private val calendarInteractor: CalendarInteractor,
    private val scheduleTimesRep: ScheduleTimesRepository,
    private val recordingsMapper: RecordingsInteractor,
    private val songsInteractor: SongsInteractor,
    private val prefs: SharedPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        CalendarViewModel::class.java ->
            CalendarViewModel(
                sessionsRep,
                calendarTimesRep,
                calendarInteractor,
                scheduleTimesRep,
                recordingsRep
            )
        RecordingsViewModel::class.java ->
            RecordingsViewModel(recordingsRep, recordingsMapper, songsInteractor)
        SessionViewModel::class.java ->
            SessionViewModel(recordingsRep, songsRep, sessionsRep, recordingsMapper)
        SongsViewModel::class.java ->
            SongsViewModel(songsRep, songsInteractor)
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}