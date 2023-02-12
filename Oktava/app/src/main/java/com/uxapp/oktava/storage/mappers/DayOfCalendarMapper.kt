package com.uxapp.oktava.storage.mappers

import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.utils.MyTime
import java.util.*

object DayOfCalendarMapper {
    fun mapToMyTimeWithoutSeconds(calendar: Calendar): MyTime {
        return MyTime(
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            0
        )
    }
}