package com.uxapp.oktava.ui.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.myViews.calendar.OktavaCalendarView
import com.uxapp.oktava.utils.TypeOfDay

class CalendarFragment: Fragment() {

    private val calendarViewModel by lazy {
        (requireActivity() as MainActivity).calendarViewModel
    }

    private lateinit var oktavaCalendarView: OktavaCalendarView
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        setupViews(view)
        childFragmentManager.beginTransaction()
            .replace(R.id.calendarFragmentContainer, CalendarPlannedFragment())
            .commit()
        return view
    }

    private fun setupViews(view: View) {
        view.apply {
            oktavaCalendarView = findViewById(R.id.calendarEditView)
            backButton = findViewById(R.id.calendarBackButton)
        }
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setupCalendar()
    }

    private fun setupCalendar() {
        oktavaCalendarView.setupDataProvider(calendarViewModel)
        oktavaCalendarView.addOnDayClickListener { day, typeOfDay ->
            calendarViewModel.setChosenDay(day)
            val fragment = when(typeOfDay) {
                TypeOfDay.PLANNED -> CalendarPlannedFragment()
                TypeOfDay.PASSED -> CalendarPassedFragment()
                TypeOfDay.NO -> CalendarEmptyFragment()
            }
            childFragmentManager.beginTransaction()
                .replace(R.id.calendarFragmentContainer, fragment)
                .commit()
        }
    }
}