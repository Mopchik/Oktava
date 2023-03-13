package com.uxapp.oktava.utils

import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.storage.model.DayOfCalendar
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.session.SessionActivity
import java.time.LocalDate
import java.time.YearMonth
import java.util.*


fun TextView.setupTextViewNotEmpty(
    text: String,
    titleTextView: TextView? = null
) {
    if (text == "") {
        this.visibility = View.GONE
        titleTextView?.visibility = View.GONE
    } else {
        this.text = text
    }
}

fun ImageView.setupImageViewFromFile(filePath: String?) {
    if (context is MainActivity && (context as MainActivity).requestPermissions() ||
        context is SessionActivity && (context as SessionActivity).requestPermissions()
    ) {
        val width = if (width > 0) width else layoutParams.width
        val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
            filePath, width
        ) ?: return
        setImageBitmap(imageBitmap)
    }
}

fun Calendar.datesEquals(calendar: Calendar): Boolean {
    return calendar.get(Calendar.YEAR) == get(Calendar.YEAR) &&
            calendar.get(Calendar.MONTH) == get(Calendar.MONTH) &&
            calendar.get(Calendar.DAY_OF_MONTH) == get(Calendar.DAY_OF_MONTH)
}

fun Calendar.datesTimesEquals(calendar: Calendar): Boolean {
    return datesEquals(calendar) &&
            get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY) &&
            get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE) &&
            get(Calendar.SECOND) == calendar.get(Calendar.SECOND)
}

fun Calendar.getDayOfWeek(): DayOfWeek {
    return when (get(Calendar.DAY_OF_WEEK)) {
        1 -> DayOfWeek.SUNDAY
        2 -> DayOfWeek.MONDAY
        3 -> DayOfWeek.TUESDAY
        4 -> DayOfWeek.WEDNESDAY
        5 -> DayOfWeek.THURSDAY
        6 -> DayOfWeek.FRIDAY
        7 -> DayOfWeek.SATURDAY
        else -> throw UnknownError("Calendar can't provide any other dayOfWeek")
    }
}

fun View.dpToPx(dp: Float): Int {
    val r: Resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.displayMetrics
    ).toInt()
}

fun Calendar.getPeriodOfDate(): Period {
    val dif = Calendar.getInstance().timeInMillis - timeInMillis
    val difDay = dif / (1000.0 * 60 * 60 * 24)
    return if (difDay <= 1) {
        Period.TODAY
    } else if (difDay <= 2) {
        Period.YESTERDAY
    } else if (difDay <= 7) {
        Period.THIS_WEEK
    } else if (difDay <= 31) {
        Period.THIS_MONTH
    } else {
        Period.BEFORE
    }
}

fun <T> mapListsToOneList(first: List<T>, second: List<T>): List<T> {
    return ArrayList<T>().apply {
        addAll(first)
        addAll(second)
    }
}

fun TextView.setTextColorRes(@ColorRes color: Int) {
    setTextColor(context.resources.getColor(color, context.theme))
}

fun DayOfCalendar.getCalendars(): List<Calendar> {
    return listOfPlanningTrainingsTime.map {
        Calendar.getInstance().apply {
            set(day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, it.hours)
            set(Calendar.MINUTE, it.minutes)
            set(Calendar.SECOND, it.seconds)
        }
    }
}

fun MyTime.addTime(time: MyTime) {
    seconds += time.seconds
    if (seconds >= 60) {
        minutes += 1
        seconds -= 60
    }
    minutes += time.minutes
    if (minutes >= 60) {
        hours += 1
        minutes -= 60
    }
    hours += time.hours
    hours %= 24
}

fun YearMonth.toCalendarString(): String {
    return if (year == YearMonth.now().year) {
        monthToString(monthValue)
    } else {
        monthToString(monthValue) + " " + year
    }
}

fun monthToString(month: Int): String {
    return when (month) {
        1 -> "Январь"
        2 -> "Февраль"
        3 -> "Март"
        4 -> "Апрель"
        5 -> "Май"
        6 -> "Июнь"
        7 -> "Июль"
        8 -> "Август"
        9 -> "Сентябрь"
        10 -> "Октябрь"
        11 -> "Ноябрь"
        12 -> "Декабрь"
        else -> throw IllegalArgumentException("No such month")
    }
}

fun LocalDate.equalsByDate(other: LocalDate): Boolean {
    return year == other.year &&
            monthValue == other.monthValue &&
            dayOfMonth == other.dayOfMonth
}

fun LocalDate.before(other: LocalDate): Boolean {
    return year < other.year ||
            (year == other.year) && monthValue < other.monthValue ||
            year == other.year && monthValue == other.monthValue && dayOfMonth < other.dayOfMonth
}

fun LocalDate.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(year, monthValue - 1, dayOfMonth)
    return calendar
}

fun Calendar.getMyTime(): MyTime {
    return MyTime(get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), get(Calendar.SECOND))
}

fun emptyCalendar(): Calendar {
    return Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 12)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
}