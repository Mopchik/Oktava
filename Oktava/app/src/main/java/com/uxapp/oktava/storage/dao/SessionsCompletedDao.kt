package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.SessionCompleted

@Dao
interface SessionsCompletedDao {
    @Insert
    suspend fun addSession(item: SessionCompleted)
    @Update
    suspend fun editSession(item: SessionCompleted)
    @Delete
    suspend fun removeSession(item: SessionCompleted)
    @Query("select * from sessions_completed")
    suspend fun all():List<SessionCompleted>
    @Query("select * from sessions_completed")
    fun getLiveData(): LiveData<List<SessionCompleted>>
    @Query("select * from sessions_completed where id == :sessionId")
    fun getRecordingsOfSessionLiveData(sessionId: Long): LiveData<SessionCompleted>
    @Query("select * from sessions_completed where id == :sessionId")
    suspend fun getRecordingsOfSession(sessionId: Long): SessionCompleted
}