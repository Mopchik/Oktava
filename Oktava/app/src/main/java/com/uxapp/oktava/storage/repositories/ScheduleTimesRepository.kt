package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.DayOfScheduleDao
import com.uxapp.oktava.storage.model.DayOfSchedule
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleTimesRepository @Inject constructor(
    private val scheduleDao: DayOfScheduleDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    val liveData = scheduleDao.getLiveData()

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