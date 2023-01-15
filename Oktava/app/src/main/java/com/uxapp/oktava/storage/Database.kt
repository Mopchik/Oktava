package com.uxapp.oktava.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uxapp.oktava.storage.dao.*
import com.uxapp.oktava.storage.model.*

@Database(
    version = 1,
    entities = [
        Song::class,
        Recording::class,
        DayOfCalendar::class,
        DayOfSchedule::class,
        SessionCompleted::class
    ],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun songsLearning(): SongsLearningDao
    abstract fun trainingTimesOfCalendar(): DayOfCalendarDao
    abstract fun trainingTimesOfSchedule(): DayOfScheduleDao
    abstract fun recordings(): RecordingDao
    abstract fun sessions(): SessionsCompletedDao
}