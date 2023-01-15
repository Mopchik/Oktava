package com.uxapp.oktava.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uxapp.oktava.utils.MyTime
import java.util.*

@Entity(tableName = "sessions_completed")
data class SessionCompleted(
    @PrimaryKey
    val id:Long,
    val recordingsID: ArrayList<String>,
    val date: Calendar,
    var duration: MyTime
)