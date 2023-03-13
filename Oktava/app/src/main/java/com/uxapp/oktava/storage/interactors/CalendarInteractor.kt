package com.uxapp.oktava.storage.interactors

import com.uxapp.oktava.storage.mappers.DayOfCalendarMapper
import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.SessionCompleted
import com.uxapp.oktava.storage.repositories.CalendarTimesRepository
import com.uxapp.oktava.storage.repositories.RecordingsRepository
import com.uxapp.oktava.storage.repositories.ScheduleTimesRepository
import com.uxapp.oktava.storage.repositories.SessionsCompletedRepository
import com.uxapp.oktava.utils.*
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import com.uxapp.oktava.viewmodels.dataModels.SessionsOfDayModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CalendarInteractor @Inject constructor(
    private val calendarTimesRep: CalendarTimesRepository,
    private val scheduleTimesRepository: ScheduleTimesRepository,
    private val sessionsCompletedRepository: SessionsCompletedRepository,
    private val recordingsInteractor: RecordingsInteractor,
    private val recordingsRepository: RecordingsRepository,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
) {

    fun getPassedDaysFlow(): Flow<List<Calendar>> {
        return sessionsCompletedRepository.sessionsCompletedFlow.map { list ->
            val listOfSessionDays = ArrayList<Calendar>()
            list.forEach { session ->
                if (listOfSessionDays.none { it.datesEquals(session.date) }) {
                    listOfSessionDays.add(session.date)
                }
            }
            listOfSessionDays
        }
    }

    fun getPlannedDaysForMonthFlow(month: Int, year: Int): Flow<List<Calendar>> {
        return combine(
            getScheduledDaysForMonthByDayOfWeekFlow(month, year),
            getScheduledDaysFromMonthDirectlyFlow(month, year),
            transform = ::mapListsToOneList
        )
    }

    fun getNearestPlanned(): Flow<Calendar> {
        val today = Calendar.getInstance()
        return calendarTimesRep.calendarTimesFlow.mapNotNull { list ->
            val calendarsList = ArrayList<Calendar>()
            list.forEach { calendarsList.addAll(it.getCalendars()) }
            val filterList = calendarsList.filter { it.timeInMillis > today.timeInMillis }
            var min = filterList.firstOrNull() ?: return@mapNotNull null
            filterList.forEach {
                if (it.timeInMillis < min.timeInMillis) min = it
            }
            min
        }
    }

    fun getPlannedTimesOfSelectedDayFlow(day: Calendar): Flow<List<MyTime>> {
        return combine(
            getMyTimesOfDayByWeekFlow(day),
            getMyTimesOfDayDirectlyFlow(day),
            transform = ::mapListsToOneList
        )
    }

    fun getSessionsInformationOfSelectedDayFlow(day: Calendar): Flow<SessionsOfDayModel> {
        return combine(
            sessionsCompletedRepository.sessionsCompletedFlow,
            recordingsRepository.recordingsFlow,
        ) { sessions, recordings ->
            mapSessionsAndRecordingsToSessionsOfDayModel(sessions, recordings, day)
        }
    }

    fun addNewTimeToCalendar(dateAndTime: Calendar) {
        applicationScope.launch(Dispatchers.IO) {
            val dayOfCalendar = calendarTimesRep.getDayOfCalendar(dateAndTime)
            if (dayOfCalendar == null) {
                addNewDayOfCalendar(dateAndTime)
            } else {
                changeExistedDayOfCalendar(dayOfCalendar, dateAndTime)
            }
        }
    }

    fun deleteTimeFromCalendar(dateAndTime: Calendar) {
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
            if (newListOfTimes.isEmpty()) {
                calendarTimesRep.deleteItem(newDayOfCalendar)
            } else {
                calendarTimesRep.changeItem(newDayOfCalendar)
            }
        }
    }

    fun changeTimeCalendar(dateFrom: Calendar, to: MyTime) {
        applicationScope.launch(Dispatchers.IO) {
            val dayOfCalendar = calendarTimesRep.getDayOfCalendar(dateFrom)
            val from = DayOfCalendarMapper.mapToMyTimeWithoutSeconds(dateFrom)

            if (dayOfCalendar == null ||
                !dayOfCalendar.listOfPlanningTrainingsTime.contains(from) ||
                dayOfCalendar.listOfPlanningTrainingsTime.contains(to)
            ) {
                return@launch
            }
            val newListOfTimes = dayOfCalendar.listOfPlanningTrainingsTime.toMutableList()
            newListOfTimes[newListOfTimes.indexOf(from)] = to
            val newDayOfCalendar = dayOfCalendar.copy(
                listOfPlanningTrainingsTime = newListOfTimes
            )
            calendarTimesRep.changeItem(newDayOfCalendar)
        }
    }

    private suspend fun mapSessionsAndRecordingsToSessionsOfDayModel(
        sessions: List<SessionCompleted>,
        recordings: List<Recording>,
        day: Calendar
    ): SessionsOfDayModel {
        val songsOfSession = HashSet<Int>()
        val steps = HashSet<Pair<Int, Int>>()
        val wholeDuration = MyTime(0, 0, 0)
        val recordingModels = ArrayList<RecordingModel>()
        val minTime = MyTime(23, 59, 59)

        sessions.filter { it.date.datesEquals(day) }.forEach { session ->
            wholeDuration.addTime(session.duration)
            session.recordingsID.forEach { path ->
                val recording = recordings.firstOrNull { it.path == path }
                val recordingModel = recordingsInteractor.mapRecordingToRecordingModel(recording)
                if (recording != null && recordingModel != null) {
                    songsOfSession.add(recording.idOfSong)
                    recordingModels.add(recordingModel)
                    steps.add(Pair(recording.idOfSong, recording.step))
                }
                val startedHour = session.date.get(Calendar.HOUR_OF_DAY)
                val startedMinute = session.date.get(Calendar.MINUTE)
                if (startedHour < minTime.hours ||
                    startedHour == minTime.hours && startedMinute < minTime.minutes
                ) {
                    minTime.hours = startedHour
                    minTime.minutes = startedMinute
                }
                day.set(Calendar.HOUR_OF_DAY, minTime.hours)
                day.set(Calendar.MINUTE, minTime.minutes)
            }
        }
        return SessionsOfDayModel(
            day = day,
            wholeDuration = wholeDuration,
            recordings = recordingModels,
            stepsCount = steps.size,
            songsCount = songsOfSession.size
        )
    }

    private fun addNewDayOfCalendar(dateAndTime: Calendar) {
        val time = DayOfCalendarMapper.mapToMyTimeWithoutSeconds(dateAndTime)
        calendarTimesRep.addNewItem(DayOfCalendar(dateAndTime, listOf(time)))
    }

    private fun changeExistedDayOfCalendar(
        dayOfCalendar: DayOfCalendar,
        dateAndTime: Calendar
    ) {
        val time = DayOfCalendarMapper.mapToMyTimeWithoutSeconds(dateAndTime)
        if (dayOfCalendar.listOfPlanningTrainingsTime.contains(time)) {
            return
        }
        val newListOfTimes = dayOfCalendar.listOfPlanningTrainingsTime.toMutableList()
        newListOfTimes.add(time)
        val newDayOfCalendar = dayOfCalendar.copy(
            listOfPlanningTrainingsTime = newListOfTimes
        )
        calendarTimesRep.changeItem(newDayOfCalendar)
    }

    private fun getMyTimesOfDayByWeekFlow(day: Calendar): Flow<List<MyTime>> {
        return scheduleTimesRepository.scheduleTimesFlow.map { list ->
            list.firstOrNull {
                it.dayOfWeek == day.getDayOfWeek()
            }?.listOfPlanningTrainingsTime ?: ArrayList()
        }
    }

    private fun getMyTimesOfDayDirectlyFlow(day: Calendar): Flow<List<MyTime>> {
        return calendarTimesRep.calendarTimesFlow.map { list ->
            list.firstOrNull { it.day.datesEquals(day) }
                ?.listOfPlanningTrainingsTime ?: ArrayList()
        }
    }

    private fun getScheduledDaysFromMonthDirectlyFlow(month: Int, year: Int): Flow<List<Calendar>> {
        val today = Calendar.getInstance()
        return calendarTimesRep.calendarTimesFlow.map { list ->
            list.filter {
                it.day.get(Calendar.MONTH) == month &&
                        it.day.get(Calendar.YEAR) == year &&
                        it.day.timeInMillis > today.timeInMillis
            }.map { it.day }
        }
    }

    private fun getScheduledDaysForMonthByDayOfWeekFlow(
        month: Int,
        year: Int
    ): Flow<List<Calendar>> {
        val firstDayOfMonth = Calendar.getInstance().apply {
            set(year, month, 1)
        }
        val dayOfWeekOfFirstDay = firstDayOfMonth.getDayOfWeek()
        val maxDayOfMonth = firstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDayOfMonth = Calendar.getInstance().apply {
            set(year, month, maxDayOfMonth)
        }
        val today = Calendar.getInstance()

        return scheduleTimesRepository.scheduleTimesFlow.map { list ->
            val daysList = ArrayList<Calendar>()
            if (today.timeInMillis >= lastDayOfMonth.timeInMillis) {
                return@map daysList
            }
            list.forEach {
                daysList.addAll(
                    getDaysOfMonthWithDayOfWeek(
                        dayOfWeekOfFirstDay,
                        it.dayOfWeek,
                        maxDayOfMonth,
                        month,
                        year
                    )
                )
            }
            if (today.timeInMillis < firstDayOfMonth.timeInMillis) {
                return@map daysList
            }
            daysList.filter { it.get(Calendar.DAY_OF_WEEK) > today.get(Calendar.DAY_OF_MONTH) }
        }
    }

    private fun getDaysOfMonthWithDayOfWeek(
        dayOfWeekOfFirstDay: DayOfWeek,
        requiredDayOfWeek: DayOfWeek,
        maxDayOfMonth: Int,
        month: Int,
        year: Int
    ): List<Calendar> {
        val listOfDaysWithDayOfWeek = ArrayList<Int>()
        val firstDayOfMonthWithDayOfWeek =
            if (dayOfWeekOfFirstDay.ordinal <= requiredDayOfWeek.ordinal) {
                1 + (requiredDayOfWeek.ordinal - dayOfWeekOfFirstDay.ordinal)
            } else {
                1 + ((requiredDayOfWeek.ordinal + 7) - dayOfWeekOfFirstDay.ordinal)
            }
        for (i in firstDayOfMonthWithDayOfWeek..maxDayOfMonth step 7) {
            listOfDaysWithDayOfWeek.add(i)
        }
        return listOfDaysWithDayOfWeek.map {
            Calendar.getInstance().apply {
                set(year, month, it)
            }
        }
    }
}