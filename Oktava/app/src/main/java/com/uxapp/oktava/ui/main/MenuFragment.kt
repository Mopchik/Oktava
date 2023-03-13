package com.uxapp.oktava.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.calendar.CalendarFragment
import com.uxapp.oktava.ui.main.notes.NotesFragment
import com.uxapp.oktava.ui.main.recordings.RecordingsFragment
import com.uxapp.oktava.ui.myViews.calendar.OktavaCalendarView
import com.uxapp.oktava.ui.session.SessionActivity
import com.uxapp.oktava.utils.StringConverter

class MenuFragment: Fragment() {

    private val calendarViewModel by lazy {
        (requireActivity() as MainActivity).calendarViewModel
    }

    private lateinit var oktavaCalendarView: OktavaCalendarView
    private lateinit var calendarContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_menu, container, false)
        val buttonBeginTraining = fragmentView.findViewById<Button>(R.id.buttonBeginTraining)
        val buttonNotes = fragmentView.findViewById<Button>(R.id.buttonNotes)
        val buttonRecordings = fragmentView.findViewById<Button>(R.id.buttonRecordings)
        val nearest = fragmentView.findViewById<TextView>(R.id.nearestExerciseTextView)
        val nearestContainer = fragmentView.findViewById<LinearLayout>(R.id.nearestContainer)
        val bubbleNotes = fragmentView.findViewById<ImageView>(R.id.bubbleFirstStepNotesImageView)

        setupBubble(bubbleNotes)

        oktavaCalendarView = fragmentView.findViewById(R.id.calendarView)
        calendarContainer = fragmentView.findViewById(R.id.menuCalendarContainer)
        setupCalendar()
        buttonBeginTraining.setOnClickListener{
            val intent = Intent(requireActivity(), SessionActivity::class.java)
            startActivity(intent)
        }
        buttonNotes.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, NotesFragment())
                .addToBackStack(null)
                .commit()
        }
        buttonRecordings.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, RecordingsFragment())
                .addToBackStack(null)
                .commit()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            calendarViewModel.getNearestPlanned().collect {
                nearestContainer.visibility = View.VISIBLE
                nearest.text = StringConverter.calendarToTimeDayMonthString(it)
            }
        }
        return fragmentView
    }

    private fun setupCalendar(){
        oktavaCalendarView.setupDataProvider(calendarViewModel)
        oktavaCalendarView.setSelectedColorResource(R.drawable.calendar_selected_day_menu)
        oktavaCalendarView.setDaysClickable(false)
        calendarContainer.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, CalendarFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupBubble(bubbleNotes: View){
        val prefs = (requireActivity() as MainActivity).bubblePrefs
        if(prefs.getBoolean("isFirstMenu", true)) {
            bubbleNotes.visibility = View.VISIBLE
            prefs.edit().putBoolean("isFirstMenu", false).apply()
        } else {
            bubbleNotes.visibility = View.GONE
        }
    }
}