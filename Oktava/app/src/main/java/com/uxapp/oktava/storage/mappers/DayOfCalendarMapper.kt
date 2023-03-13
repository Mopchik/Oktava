package com.uxapp.oktava.storage.mappers

import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.utils.MyTime
import java.util.*

object DayOfCalendarMapper {
    fun mapToMyTimeWithoutSeconds(calendar: Calendar): MyTime {
        return MyTime(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            0
        )
    }
}