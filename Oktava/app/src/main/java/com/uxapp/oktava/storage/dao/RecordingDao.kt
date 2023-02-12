package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordingDao {
    @Insert
    suspend fun addRecording(item: Recording)
    @Update
    suspend fun editRecording(item: Recording)
    @Delete
    suspend fun removeRecording(item: Recording)
    @Query("DELETE FROM recordings WHERE path = :path")
    suspend fun removeRecordingByPath(path: String)
    @Query("select * from recordings")
    suspend fun all():List<Recording>
    @Query("select * from recordings")
    fun getFlow(): Flow<List<Recording>>
    @Query("select * from recordings where idOfSong = :songId")
    fun getRecordingsOfSongFlow(songId: Int): Flow<List<Recording>>
    @Query("select * from recordings where path = :path")
    suspend fun getRecordingByPath(path: String): Recording?
}