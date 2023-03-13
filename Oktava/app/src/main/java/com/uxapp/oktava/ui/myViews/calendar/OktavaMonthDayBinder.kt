package com.uxapp.oktava.ui.myViews.calendar

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.uxapp.oktava.R
import com.uxapp.oktava.utils.*
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

class OktavaMonthDayBinder : MonthDayBinder<DayViewContainer> {

    private val today = LocalDate.now()
    private var selectedDate: LocalDate = today

    private var plannedList: List<Calendar> = emptyList()
    private var passedList: List<Calendar> = emptyList()

    private val onDayClickListeners = ArrayList<(day: LocalDate, typeOfDay: TypeOfDay) -> Unit>()

    @DrawableRes
    private var selectedColorRes = R.drawable.calendar_selected_day

    // Called only when a new container is needed.
    override fun create(view: View) = DayViewContainer(view)

    // Called every time we need to reuse a container.
    override fun bind(container: DayViewContainer, data: CalendarDay) {
        val textView = container.textView
        val dotView = container.dotView

        container.view.setOnClickListener { onDayClicked(data.date) }

        textView.text = data.date.dayOfMonth.toString()

        if (data.position == DayPosition.MonthDate) {
            textView.visibility = View.VISIBLE
            when (data.date) {
                selectedDate -> {
                    textView.setTextColorRes(R.color.black)
                    textView.setBackgroundResource(selectedColorRes)
                    dotView.visibility = View.GONE
                }
                today -> {
                    textView.setTextColorRes(R.color.black)
                    textView.background = null
                    dotView.visibility = View.VISIBLE
                }
                else -> {
                    dotView.visibility = View.GONE
                    if (plannedList.any {data.date.toCalendar().datesEquals(it) }) {
                        textView.setTextColorRes(R.color.white)
                        textView.setBackgroundResource(R.drawable.calendar_planned_day)
                    } else if (passedList.any {data.date.toCalendar().datesEquals(it) }) {
                        textView.setTextColorRes(R.color.black)
                        textView.setBackgroundResource(R.drawable.calendar_passed_day)
                    } else {
                        textView.setTextColorRes(R.color.black)
                        textView.background = null
                    }
                }
            }
        } else {
            textView.visibility = View.GONE
            dotView.visibility = View.GONE
        }
    }

    fun updatePlannedList(list: List<Calendar>) {
        plannedList = list
    }

    fun updatePassedList(list: List<Calendar>) {
        passedList = list
    }

    fun addOnDayClickedListener(onDayClickListener: (day: LocalDate, typeOfDay: TypeOfDay) -> Unit) {
        this.onDayClickListeners.add(onDayClickListener)
    }

    fun setSelectedColorResource(@DrawableRes newRes: Int) {
        selectedColorRes = newRes
    }

    private fun onDayClicked(day: LocalDate) {
        if(selectedDate.equalsByDate(day)) {
            return
        }
        selectedDate = day
        onDayClickListeners.forEach {
            val typeOfDay = if(!day.before(LocalDate.now())) {
                TypeOfDay.PLANNED
            } else if(passedList.any {passedDay -> day.toCalendar().datesEquals(passedDay) }) {
                TypeOfDay.PASSED
            } else {
                TypeOfDay.NO
            }
            it.invoke(day, typeOfDay)
        }
    }
}