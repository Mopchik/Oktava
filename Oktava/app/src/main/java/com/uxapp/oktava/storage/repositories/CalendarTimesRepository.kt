package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.DayOfCalendarDao
import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarTimesRepository @Inject constructor(
    private val calendarTimesDao: DayOfCalendarDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope){
    val liveData = calendarTimesDao.getLiveData()

    fun addNewItem(item: DayOfCalendar){
        applicationScope.launch(Dispatchers.IO) {
            calendarTimesDao.addDayOfCalendar(item)
        }
    }

    fun deleteItem(item: DayOfCalendar) {
        applicationScope.launch(Dispatchers.IO) {
            calendarTimesDao.removeDayOfCalendar(item)
        }
    }

    fun changeItem(newItem: DayOfCalendar){
        applicationScope.launch(Dispatchers.IO) {
            calendarTimesDao.editDayOfCalendar(newItem)
        }
    }
}