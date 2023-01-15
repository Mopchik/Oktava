package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.DayOfCalendar

@Dao
interface DayOfCalendarDao {
    @Insert
    suspend fun addDayOfCalendar(item: DayOfCalendar)
    @Update
    suspend fun editDayOfCalendar(item: DayOfCalendar)
    @Delete
    suspend fun removeDayOfCalendar(item: DayOfCalendar)
    @Query("select * from calendar_times")
    suspend fun all():List<DayOfCalendar>
    @Query("select * from calendar_times")
    fun getLiveData(): LiveData<List<DayOfCalendar>>
}