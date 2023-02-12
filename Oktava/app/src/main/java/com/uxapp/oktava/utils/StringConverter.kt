package com.uxapp.oktava.utils

import java.util.*

object StringConverter {
    fun genreToString(genre: Genre): String{
        return when(genre){
            Genre.POP -> "Поп"
            Genre.RAP -> "Рэп"
            Genre.HIP_HOP -> "Хип-хоп"
            Genre.R_N_B -> "R'n'B"
            Genre.INDI -> "Инди"
            Genre.ROK -> "Рок"
            Genre.PUNK -> "Панк"
            Genre.METAL -> "Метал"
            Genre.ALTERNATIVE -> "Альтернатива"
            Genre.JAZZ -> "Джаз"
            Genre.BLUES -> "Блюз"
            Genre.COUNTRY -> "Кантри"
            Genre.SKA -> "Ска"
            Genre.FOLK -> "Фолк"
            Genre.CHANSON -> "Шансон"
            Genre.REGGI -> "Регги"
            Genre.CHILDHOOD -> "Детство"
            Genre.SOUNDTRACK -> "Саундтрек"
        }
    }

    fun genreFromString(genreName: String): Genre {
        return when(genreName){
            "Поп" -> Genre.POP
            "Рэп" -> Genre.RAP
            "Хип-хоп" -> Genre.HIP_HOP
            "R'n'B" -> Genre.R_N_B
            "Инди" -> Genre.INDI
            "Рок" -> Genre.ROK
            "Панк" -> Genre.PUNK
            "Метал" -> Genre.METAL
            "Альтернатива" -> Genre.ALTERNATIVE
            "Джаз" -> Genre.JAZZ
            "Блюз" -> Genre.BLUES
            "Кантри" -> Genre.COUNTRY
            "Ска" -> Genre.SKA
            "Фолк" -> Genre.FOLK
            "Шансон" -> Genre.CHANSON
            "Регги" -> Genre.REGGI
            "Детство" -> Genre.CHILDHOOD
            "Саундтрек" -> Genre.SOUNDTRACK
            else -> throw IllegalArgumentException("No genre match to input string.")
        }
    }

    fun calendarToDayMonthString(calendar: Calendar): String{
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = when(calendar.get(Calendar.MONTH)){
            0 -> "января"
            1 -> "февраля"
            2 -> "марта"
            3 -> "апреля"
            4 -> "мая"
            5 -> "июня"
            6 -> "июля"
            7 -> "августа"
            8 -> "сентября"
            9 -> "октября"
            10 -> "ноября"
            11 -> "декабря"
            else -> throw IllegalArgumentException("Нет такого месяца.")
        }
        return "$day $month"
    }

    fun myTimeToMinuteSecondString(myTime: MyTime): String {
        val minutes = if(myTime.minutes / 10 > 0) myTime.minutes.toString() else "0${myTime.minutes}"
        val seconds = if(myTime.seconds / 10 > 0) myTime.seconds.toString() else "0${myTime.seconds}"
        return "$minutes:$seconds"
    }

    fun recordingsStringByCount(recordingsCount: Int): String {
        return "$recordingsCount " +
                when (recordingsCount) {
                    1 -> "запись"
                    in 2..4 -> "записи"
                    else -> "записей"
                }
    }
}