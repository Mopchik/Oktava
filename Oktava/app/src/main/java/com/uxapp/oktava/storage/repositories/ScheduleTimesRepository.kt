package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.DayOfScheduleDao
import com.uxapp.oktava.storage.model.DayOfSchedule
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleTimesRepository @Inject constructor(
    private val scheduleDao: DayOfScheduleDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    private var daysOfSchedule: List<DayOfSchedule> = ArrayList()
    private val scheduleTimesMutableStateFlow = MutableStateFlow<List<DayOfSchedule>>(ArrayList())
    val scheduleTimesFlow: Flow<List<DayOfSchedule>> = scheduleTimesMutableStateFlow

    init{
        applicationScope.launch(Dispatchers.IO){
            scheduleDao.getFlow().collect {
                daysOfSchedule = it
                scheduleTimesMutableStateFlow.emit(it)
            }
        }
    }

    fun addNewItem(item: DayOfSchedule){
        applicationScope.launch(Dispatchers.IO) {
            scheduleDao.addDayOfSchedule(item)
        }
    }

    fun deleteItem(item: DayOfSchedule) {
        applicationScope.launch(Dispatchers.IO) {
            scheduleDao.removeDayOfSchedule(item)
        }
    }

    fun changeItem(newItem: DayOfSchedule){
        applicationScope.launch(Dispatchers.IO) {
            scheduleDao.editDayOfSchedule(newItem)
        }
    }
}