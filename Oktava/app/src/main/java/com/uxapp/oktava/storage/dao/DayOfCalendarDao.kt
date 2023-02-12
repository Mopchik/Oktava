package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.DayOfCalendar
import kotlinx.coroutines.flow.Flow
import java.util.*

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
    fun getFlow(): Flow<List<DayOfCalendar>>
    @Query("select * from calendar_times where day = :calendar")
    suspend fun getDayOfCalendar(calendar: Calendar): List<DayOfCalendar>
}