package com.uxapp.oktava.utils

const val APP_PREFERENCES = "oktava_preferences"
const val MAX_STEP = 7

enum class DayOfWeek{
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

enum class Period {
    TODAY, YESTERDAY, THIS_WEEK, THIS_MONTH, BEFORE
}

enum class Genre{
    POP, RAP, HIP_HOP, R_N_B,
    INDI,ROK, PUNK, METAL,
    ALTERNATIVE, JAZZ, BLUES,
    COUNTRY, SKA, FOLK, CHANSON,
    REGGI, CHILDHOOD, SOUNDTRACK;
}