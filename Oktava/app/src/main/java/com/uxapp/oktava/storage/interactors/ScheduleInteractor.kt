package com.uxapp.oktava.storage.interactors

import com.uxapp.oktava.storage.model.DayOfSchedule
import com.uxapp.oktava.storage.repositories.ScheduleTimesRepository
import com.uxapp.oktava.utils.DayOfWeek
import com.uxapp.oktava.utils.Layer
import com.uxapp.oktava.utils.MyTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleInteractor @Inject constructor(
    private val scheduleTimesRepository: ScheduleTimesRepository,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
) {
    fun planWeeksTraining(weekTimes: List<Pair<DayOfWeek, MyTime>>) {
        applicationScope.launch(Dispatchers.IO) {
            weekTimes.forEach {
                val dayScheduler = scheduleTimesRepository.getDayOfScheduleByDayOfWeek(it.first)
                if (dayScheduler == null) {
                    scheduleTimesRepository.addNewItem(DayOfSchedule(it.first, listOf(it.second)))
                } else {
                    val planningTimes = dayScheduler.listOfPlanningTrainingsTime.toMutableList()
                    if (!planningTimes.contains(it.second)) {
                        planningTimes.add(it.second)
                    }
                    scheduleTimesRepository.changeItem(
                        dayScheduler.copy(
                            listOfPlanningTrainingsTime = planningTimes
                        )
                    )
                }
            }
        }
    }
}