package com.uxapp.oktava.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxapp.oktava.utils.DayOfWeek
import com.uxapp.oktava.utils.MyTime

@Entity(tableName = "schedule_times")
data class DayOfSchedule(
    @PrimaryKey
    val dayOfWeek: DayOfWeek,
    val listOfPlanningTrainingsTime: List<MyTime>
)