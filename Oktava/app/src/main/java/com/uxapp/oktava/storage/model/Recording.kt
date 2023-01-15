package com.uxapp.oktava.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxapp.oktava.utils.MyTime
import java.util.*

@Entity(tableName = "recordings")
data class Recording(
    @PrimaryKey
    val path: String,
    val idOfSong: Int,
    val description: String,
    val step: Int,
    val date: Calendar,
    val duration: MyTime
)