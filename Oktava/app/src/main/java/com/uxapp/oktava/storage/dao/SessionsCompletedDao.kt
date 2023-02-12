package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.SessionCompleted
import kotlinx.coroutines.flow.Flow

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
    fun getSessionsCompletedFlow(): Flow<List<SessionCompleted>>
    @Query("select * from sessions_completed where id == :sessionId")
    fun getSessionByIdFlow(sessionId: Long): Flow<List<SessionCompleted>>
    @Query("select * from sessions_completed where id == :sessionId")
    suspend fun getSessionById(sessionId: Long): List<SessionCompleted>
}