package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.Recording

@Dao
interface RecordingDao {
    @Insert
    suspend fun addRecording(item: Recording)
    @Update
    suspend fun editRecording(item: Recording)
    @Delete
    suspend fun removeRecording(item: Recording)
    @Query("select * from recordings")
    suspend fun all():List<Recording>
    @Query("select * from recordings")
    fun getLiveData(): LiveData<List<Recording>>
}