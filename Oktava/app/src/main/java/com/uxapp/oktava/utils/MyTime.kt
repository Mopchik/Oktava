package com.uxapp.oktava.utils

data class MyTime(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
){
    companion object{
        fun from(timeInMillis: Long): MyTime{
            var temp = timeInMillis / 1000
            val hour = temp / 3600
            temp %= 3600
            val minute = temp / 60
            temp %= 60
            val second = temp
            return MyTime(hour.toInt(), minute.toInt(), second.toInt())
        }
    }
}