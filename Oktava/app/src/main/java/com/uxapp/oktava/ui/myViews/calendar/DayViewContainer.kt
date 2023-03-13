package com.uxapp.oktava.ui.myViews.calendar

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer
import com.uxapp.oktava.R

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)
    val dotView = view.findViewById<View>(R.id.dotView)
}