package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import com.uxapp.oktava.storage.interactors.CalendarInteractor
import com.uxapp.oktava.storage.repositories.CalendarTimesRepository
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.storage.repositories.ScheduleTimesRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository
import com.uxapp.oktava.utils.MyTime
import com.uxapp.oktava.viewmodels.dataModels.SessionsOfDayModel
import kotlinx.coroutines.flow.Flow
import java.util.*

class CalendarViewModel(
    private val sessionsRep: SessionsCompletedRepository,
    private val calendarTimesRep: CalendarTimesRepository,
    private val calendarInteractor: CalendarInteractor,
    private val scheduleTimesRep: ScheduleTimesRepository,
    private val recordingsRepository: RecordingsRepository
): ViewModel() {

    val calendarTimesFlow = calendarTimesRep.calendarTimesFlow

    fun planOneNewTraining(dateAndTime: Calendar){
        calendarInteractor.addNewTimeToCalendar(dateAndTime)
    }

    fun deleteOneTraining(dateAndTime: Calendar){
        calendarInteractor.deleteTimeFromCalendar(dateAndTime)
    }

    fun editOneTime(dateAndTimeFrom: Calendar, dateAndTimeTo: Calendar){
        deleteOneTraining(dateAndTimeFrom)
        planOneNewTraining(dateAndTimeTo)
    }

    // fun getPlannedDaysFlow(): Flow<List<Calendar>> {
    //
    // }
//
    // fun getPassedDaysFlow(): Flow<List<Calendar>> {
//
    // }
//
    // fun getPlannedTimesOfSelectedDay(day: Calendar): Flow<List<MyTime>>{
//
    // }
//
    // fun getSessionsInformationOfSelectedDay(day: Calendar): SessionsOfDayModel {
//
    // }
//
    // fun deleteRecording(path: String) {
    //     recordingsRepository.deleteItemByPath(path)
    // }
}