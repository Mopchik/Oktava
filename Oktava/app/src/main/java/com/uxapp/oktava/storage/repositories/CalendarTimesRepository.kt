package com.uxapp.oktava.storage.repositories

import com.uxapp.oktava.storage.dao.DayOfCalendarDao
import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.NoSuchElementException

class CalendarTimesRepository @Inject constructor(
    private val calendarTimesDao: DayOfCalendarDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope){

    init {
        clean()
        applicationScope.launch(Dispatchers.IO) {
            calendarTimesDao.getFlow().collect{
                daysOfCalendar = it.toList()
                calendarTimesMutableFlow.emit(it)
            }
        }
    }

    private var daysOfCalendar: List<DayOfCalendar> = ArrayList()
    private val calendarTimesMutableFlow = MutableStateFlow<List<DayOfCalendar>>(ArrayList())
    val calendarTimesFlow: Flow<List<DayOfCalendar>> = calendarTimesMutableFlow

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

    fun getDayOfCalendar(calendar: Calendar): DayOfCalendar? {
        val daysOfCalendarCopy = daysOfCalendar.toList()
        return try{
            daysOfCalendarCopy.first { it.day.timeInMillis == calendar.timeInMillis }
        } catch(e: NoSuchElementException){
            null
        }
    }

    fun clean(){
        applicationScope.launch {
            val allCalendarDays = calendarTimesDao.all()
            val now = Calendar.getInstance().timeInMillis
            allCalendarDays.forEach {
                if(it.day.timeInMillis < now){
                    deleteItem(it)
                }
            }
        }
    }
}