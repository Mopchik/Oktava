package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxapp.oktava.storage.interactors.CalendarInteractor
import com.uxapp.oktava.storage.interactors.ScheduleInteractor
import com.uxapp.oktava.storage.repositories.CalendarTimesRepository
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.storage.repositories.ScheduleTimesRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository
import com.uxapp.oktava.utils.*
import com.uxapp.oktava.viewmodels.dataModels.SessionsOfDayModel
import com.uxapp.oktava.viewmodels.interfaces.CalendarDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class CalendarViewModel(
    private val scheduleInteractor: ScheduleInteractor,
    private val calendarInteractor: CalendarInteractor,
    private val recordingsRepository: RecordingsRepository
) : ViewModel(), CalendarDataProvider {

    private var chosenDay: LocalDate = LocalDate.now()

    var isChangingExercise = false
        private set
    var dateOfChangingExercise: Calendar = emptyCalendar()

    fun setChosenDay(chosenDay: LocalDate) {
        this.chosenDay = chosenDay
    }

    fun planOneNewTraining(time: MyTime) {
        val dateAndTime =
            chosenDay.toCalendar().apply {
                set(Calendar.HOUR_OF_DAY, time.hours)
                set(Calendar.MINUTE, time.minutes)
                set(Calendar.SECOND, time.seconds)
            }
        calendarInteractor.addNewTimeToCalendar(dateAndTime)
    }

    fun planWeeksTraining(weekTimes: List<Pair<DayOfWeek, MyTime>>) {
        scheduleInteractor.planWeeksTraining(weekTimes)
    }

    fun deleteOneTraining(time: MyTime) {
        val dateAndTime =
            chosenDay.toCalendar().apply {
                set(Calendar.HOUR_OF_DAY, time.hours)
                set(Calendar.MINUTE, time.minutes)
                set(Calendar.SECOND, time.seconds)
            }
        calendarInteractor.deleteTimeFromCalendar(dateAndTime)
    }

    fun editOneTime(timeTo: MyTime) {
        calendarInteractor.changeTimeCalendar(dateOfChangingExercise, timeTo)
        isChangingExercise = false
        dateOfChangingExercise = emptyCalendar()
    }

    fun changeDateStart(oldDate: Calendar) {
        isChangingExercise = true
        dateOfChangingExercise = oldDate
    }

    override fun getPlannedDaysForMonthFlow(month: Int, year: Int): Flow<List<Calendar>> {
        // return MutableStateFlow(listOf(23, 26))
        return calendarInteractor.getPlannedDaysForMonthFlow(month - 1, year)
    }

    override fun getPassedDaysForMonthFlow(month: Int, year: Int): Flow<List<Calendar>> {
        // return MutableStateFlow(listOf(1,5,8,19, 20))
        return calendarInteractor.getPassedDaysFlow().map { list ->
            list.filter {
                it.get(Calendar.MONTH) == month - 1 &&
                        it.get(Calendar.YEAR) == year
            }
        }
    }

    fun getPlannedTimesOfSelectedDay(): Flow<List<Calendar>> {
        // val first = Calendar.getInstance().apply {
        //     set(Calendar.HOUR, 12)
        //     set(Calendar.MINUTE, 30)
        // }
        // val second = Calendar.getInstance().apply {
        //     set(Calendar.HOUR, 15)
        //     set(Calendar.MINUTE, 40)
        // }
        // val third = Calendar.getInstance().apply {
        //     set(Calendar.HOUR, 17)
        //     set(Calendar.MINUTE, 55)
        // }
        // return MutableStateFlow(
        //     listOf(first, second, third)
        // )
        return calendarInteractor.getPlannedTimesOfSelectedDayFlow(chosenDay.toCalendar())
            .map { list ->
                list.map {
                    chosenDay.toCalendar().apply {
                        set(Calendar.HOUR_OF_DAY, it.hours)
                        set(Calendar.MINUTE, it.minutes)
                        set(Calendar.SECOND, it.seconds)
                    }
                }
            }
    }

    fun getSessionsInformationOfSelectedDayFlow(): Flow<SessionsOfDayModel> {
        return calendarInteractor.getSessionsInformationOfSelectedDayFlow(chosenDay.toCalendar())
    }

    fun deleteRecording(path: String) {
        recordingsRepository.deleteItemByPath(path)
    }

    fun getNearestPlanned(): Flow<Calendar> {
        return calendarInteractor.getNearestPlanned()
    }
}