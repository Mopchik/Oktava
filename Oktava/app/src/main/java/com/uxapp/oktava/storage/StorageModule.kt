package com.uxapp.oktava.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.uxapp.oktava.storage.dao.*
import com.uxapp.oktava.utils.*
import dagger.Module
import dagger.Provides

@Module
object StorageModule {
    @Provides
    @AppScope
    fun provideDataBase(@ContextOwnerQualifier(Layer.APP)
                        applicationContext: Context
    ): Database {
        return Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "oktava_database.db").build()
    }

    @Provides
    @AppScope
    fun provideDayOfCalendarDao(db: Database): DayOfCalendarDao {
        return db.trainingTimesOfCalendar()
    }

    @Provides
    @AppScope
    fun provideDayOfScheduleDao(db: Database): DayOfScheduleDao {
        return db.trainingTimesOfSchedule()
    }

    @Provides
    @AppScope
    fun provideRecordingDao(db: Database): RecordingDao {
        return db.recordings()
    }

    @Provides
    @AppScope
    fun provideSessionCompletedDao(db: Database): SessionsCompletedDao {
        return db.sessions()
    }

    @Provides
    @AppScope
    fun provideSongsLearningDao(db: Database): SongsLearningDao {
        return db.songsLearning()
    }

    @Provides
    @AppScope
    fun provideSharedPreferences(@ContextOwnerQualifier(Layer.APP)
                                 applicationContext: Context
    ): SharedPreferences {
        return applicationContext.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE)
    }
}