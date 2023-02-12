package com.uxapp.oktava.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uxapp.oktava.storage.model.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsLearningDao {
    @Insert
    suspend fun addSong(item: Song)
    @Update
    suspend fun editSong(item: Song)
    @Delete
    suspend fun removeSong(item: Song)
    @Query("delete from songs_learning where id = :id")
    suspend fun removeSongById(id: Int)
    @Query("select * from songs_learning")
    suspend fun all():List<Song>
    @Query("select * from songs_learning")
    fun getFlow(): Flow<List<Song>>
    @Query("select * from songs_learning where id = :songId")
    suspend fun getSongById(songId: Int): List<Song>
    @Query("select * from songs_learning where id = :songId")
    fun getSongByIdFlow(songId: Int): Flow<List<Song>>
}