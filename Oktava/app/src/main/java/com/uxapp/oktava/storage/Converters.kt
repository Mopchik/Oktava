package com.uxapp.oktava.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uxapp.oktava.utils.Genre
import com.uxapp.oktava.utils.MyTime
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class Converters {
    @TypeConverter
    fun toCalendar(strArray: String?): Calendar? {
        if(strArray == null) return null
        val splitArray = strArray.split(' ')
        if(splitArray.size != 6)
            throw IllegalArgumentException("IntArray should contains day, month and year.")
        val intArray = IntArray(splitArray.size)
        for(i in splitArray.indices){
            intArray[i] = splitArray[i].toInt()
        }
        val day = intArray[0]
        val month = intArray[1]
        val year = intArray[2]
        val hour = intArray[3]
        val minute = intArray[4]
        val second = intArray[5]
        return Calendar.getInstance().apply{
            set(year, month, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)}
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): String?{
        return if(calendar == null) null
        else calendar.get(Calendar.DAY_OF_MONTH).toString() + " " +
                calendar.get(Calendar.MONTH) + " " +
                calendar.get(Calendar.YEAR) + " " +
                calendar.get(Calendar.HOUR_OF_DAY) + " " +
                calendar.get(Calendar.MINUTE) + " " +
                calendar.get(Calendar.SECOND)
    }

    @TypeConverter
    fun fromGenreList(listOfGenres: List<Genre>): String{
        return Gson().toJson(listOfGenres)
    }

    @TypeConverter
    fun toGenreList(strArray: String): List<Genre>{
        val listType: Type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(strArray, listType)
    }

    @TypeConverter
    fun fromMyTime(myTime: MyTime): String{
        return myTime.hours.toString() + " " + myTime.minutes + " " + myTime.seconds
    }

    @TypeConverter
    fun toMyTime(strArray: String): MyTime{
        val split = strArray.split(" ")
        return MyTime(split[0].toInt(), split[1].toInt(), split[2].toInt())
    }

    @TypeConverter
    fun fromMyTimeList(listOfMyTime: List<MyTime>): String{
        return Gson().toJson(listOfMyTime)
    }

    @TypeConverter
    fun toMyTimeList(strArray: String): List<MyTime>{
        val listType: Type = object : TypeToken<List<MyTime>>() {}.type
        return Gson().fromJson(strArray, listType)
    }

    @TypeConverter
    fun fromListString(list: List<String>): String{
        val strBuilder = StringBuilder()
        list.forEach {
            strBuilder.append("$it||||||||##")
        }
        return strBuilder.toString()
    }

    @TypeConverter
    fun toListString(listAsString: String): ArrayList<String>{
        val result = ArrayList<String>()
        listAsString.split("||||||||##").forEach {
            result.add(it)
        }
        return result
    }
}