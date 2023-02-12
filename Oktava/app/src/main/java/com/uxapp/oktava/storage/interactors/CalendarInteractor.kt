package com.uxapp.oktava.storage.interactors

import com.uxapp.oktava.storage.mappers.DayOfCalendarMapper
import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.storage.repositories.CalendarTimesRepository
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CalendarInteractor @Inject constructor(
    private val calendarTimesRep: CalendarTimesRepository,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
) {
    fun addNewTimeToCalendar(dateAndTime: Calendar){
        applicationScope.launch(Dispatchers.IO) {
            val dayOfCalendar = calendarTimesRep.getDayOfCalendar(dateAndTime)
            if (dayOfCalendar == null) {
                addNewDayOfCalendar(dateAndTime)
            } else {
                changeExistedDayOfCalendar(dayOfCalendar, dateAndTime)
            }
        }
    }

    fun deleteTimeFromCalendar(dateAndTime: Calendar){
        applicationScope.launch(Dispatchers.IO) {
            val dayOfCalendar = calendarTimesRep.getDayOfCalendar(dateAndTime)
            val time = DayOfCalendarMapper.mapToMyTimeWithoutSeconds(dateAndTime)
            if (dayOfCalendar == null ||
                !dayOfCalendar.listOfPlanningTrainingsTime.contains(time)
            ) {
                return@launch
            }
            val newListOfTimes = dayOfCalendar.listOfPlanningTrainingsTime.toMutableList()
            newListOfTimes.remove(time)
            val newDayOfCalendar = dayOfCalendar.copy(
                listOfPlanningTrainingsTime = newListOfTimes
            )
            calendarTimesRep.changeItem(newDayOfCalendar)
        }
    }

    private fun addNewDayOfCalendar(dateAndTime: Calendar){
        val time = DayOfCalendarMapper.mapToMyTimeWithoutSeconds(dateAndTime)
        calendarTimesRep.addNewItem(DayOfCalendar(dateAndTime, listOf(time)))
    }

    private fun changeExistedDayOfCalendar(
        dayOfCalendar: DayOfCalendar,
        dateAndTime: Calendar
    ) {
        val time = DayOfCalendarMapper.mapToMyTimeWithoutSeconds(dateAndTime)
        if(dayOfCalendar.listOfPlanningTrainingsTime.contains(time)){
            return
        }
        val newListOfTimes = dayOfCalendar.listOfPlanningTrainingsTime.toMutableList()
        newListOfTimes.add(time)
        val newDayOfCalendar = dayOfCalendar.copy(
            listOfPlanningTrainingsTime = newListOfTimes
        )
        calendarTimesRep.changeItem(newDayOfCalendar)
    }
}