package com.uxapp.oktava.ui.myViews.calendar

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MarginValues
import com.uxapp.oktava.R
import com.uxapp.oktava.utils.TypeOfDay
import com.uxapp.oktava.utils.dpToPx
import com.uxapp.oktava.utils.toCalendarString
import com.uxapp.oktava.viewmodels.interfaces.CalendarDataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class OktavaCalendarView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    private val calendarView = CalendarView(context)
    private val monthTextView = TextView(context)

    private val lifecycleScope = CoroutineScope(Dispatchers.Main)

    private var isClickableView = true

    private val binder by lazy {
        OktavaMonthDayBinder()
    }

    private var calendarDataProvider: CalendarDataProvider? = null

    init {
        orientation = VERTICAL
        val monthParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(35, 25, 0, 0)
        }
        monthTextView.apply {
            layoutParams = monthParams
            text = YearMonth.now().toCalendarString()
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
            typeface = Typeface.DEFAULT_BOLD;
        }
        calendarView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        calendarView.dayViewResource = R.layout.calendar_day_layout
        setupCalendar()
        addView(monthTextView)
        addView(calendarView)
    }


    fun setupDataProvider(provider: CalendarDataProvider) {
        calendarDataProvider = provider
    }

    fun setSelectedColorResource(@DrawableRes newRes: Int) {
        binder.setSelectedColorResource(newRes)
    }

    fun setDaysClickable(clickable: Boolean) {
        isClickableView = clickable
    }

    fun addOnDayClickListener(onDayClicked: (day: LocalDate, typeOfDay: TypeOfDay) -> Unit) {
        binder.addOnDayClickedListener(onDayClicked)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val default = super.dispatchTouchEvent(ev)
        return if(ev.action == ev.action) {
            isClickableView
        } else {
            default
        }
    }

    private fun setupCalendar() {
        calendarView.apply {
            val currentMonth = YearMonth.now()
            val startMonth = currentMonth.minusMonths(100)  // Adjust as needed
            val endMonth = currentMonth.plusMonths(100)  // Adjust as needed
            val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
            setup(startMonth, endMonth, firstDayOfWeek)
            scrollToMonth(currentMonth)
            observeFlowsForMonth(currentMonth)
            binder.addOnDayClickedListener { day, _ ->
                notifyMonthChanged(day.yearMonth)
            }
            dayBinder = binder
            monthScrollListener = ::onMonthScrolled
            scrollPaged = true
            outDateStyle = OutDateStyle.EndOfGrid
            val horizontalMargin = dpToPx(8f)
            val verticalMargin = dpToPx(8f)
            monthMargins = MarginValues(
                start = horizontalMargin,
                end = horizontalMargin,
                top = verticalMargin,
                bottom = verticalMargin,
            )
        }
    }

    private fun observeFlowsForMonth(month: YearMonth) {
        lifecycleScope.launch {
            calendarDataProvider?.getPassedDaysForMonthFlow(month.monthValue, month.year)
                ?.collect {
                    binder.updatePassedList(it)
                    calendarView.notifyMonthChanged(month)
                }
        }
        lifecycleScope.launch {
            calendarDataProvider?.getPlannedDaysForMonthFlow(month.monthValue, month.year)
                ?.collect {
                    binder.updatePlannedList(it)
                    calendarView.notifyMonthChanged(month)
                }
        }
    }

    private fun onMonthScrolled(newMonth: CalendarMonth) {
        observeFlowsForMonth(newMonth.yearMonth)
        monthTextView.text = newMonth.yearMonth.toCalendarString()
    }
}