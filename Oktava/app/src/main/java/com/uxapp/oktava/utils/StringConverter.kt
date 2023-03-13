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

    fun calendarToTimeDayMonthString(calendar: Calendar): String {
        return calendarToDayMonthString(calendar) + " " +
                intsToTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
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
        return intsToTime(myTime.minutes, myTime.seconds)
    }

    fun intsToTime(first: Int, second: Int): String {
        val minutes = if(first / 10 > 0) first.toString() else "0${first}"
        val seconds = if(second / 10 > 0) second.toString() else "0${second}"
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

    fun getStringHours(h: Int): String {
        return "$h " + when(h) {
            1 -> "час"
            2,3,4 -> "часа"
            else -> "часов"
        }
    }

    fun getStringMinutes(m: Int): String {
        return "$m " + when(m) {
            1,21,31,41,51 -> "минуту"
            11,12,13,14 -> "минут"
            else -> {
                when(m%10) {
                    2, 3, 4 -> "минуты"
                    else -> "минут"
                }
            }
        }
    }

    fun getStringSteps(s: Int): String {
        return "$s " + when(s) {
            11, 12, 13, 14 -> "шагов"
            else -> {
                when(s%10) {
                    0, 5, 6, 7, 8, 9 -> "шагов"
                    1 -> "шаг"
                    else -> "шага"
                }
            }
        }
    }

    fun getStringSongs(s: Int): String {
        return "$s " + if(s % 10 == 1 && s != 11) {
            "песне"
        } else {
            "песнях"
        }
    }
}