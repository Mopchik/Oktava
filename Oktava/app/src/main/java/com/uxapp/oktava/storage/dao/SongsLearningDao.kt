package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.Song

@Dao
interface SongsLearningDao {
    @Insert
    suspend fun addSong(item: Song)
    @Update
    suspend fun editSong(item: Song)
    @Delete
    suspend fun removeSong(item: Song)
    @Query("select * from songs_learning")
    suspend fun all():List<Song>
    @Query("select * from songs_learning")
    fun getLiveData(): LiveData<List<Song>>
    @Query("select * from songs_learning where id = :songId")
    suspend fun getSongById(songId: Int): Song
}