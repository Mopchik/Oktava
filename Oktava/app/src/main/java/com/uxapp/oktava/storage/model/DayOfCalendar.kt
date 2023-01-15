package com.uxapp.oktava.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxapp.oktava.utils.MyTime
import java.util.*

@Entity(tableName = "calendar_times")
data class DayOfCalendar(
    @PrimaryKey
    val day: Calendar,
    val listOfPlanningTrainingsTime: List<MyTime>
)