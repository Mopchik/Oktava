package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.DayOfSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface DayOfScheduleDao {
    @Insert
    suspend fun addDayOfSchedule(item: DayOfSchedule)
    @Update
    suspend fun editDayOfSchedule(item: DayOfSchedule)
    @Delete
    suspend fun removeDayOfSchedule(item: DayOfSchedule)
    @Query("select * from schedule_times")
    suspend fun all():List<DayOfSchedule>
    @Query("select * from schedule_times")
    fun getFlow(): Flow<List<DayOfSchedule>>
}