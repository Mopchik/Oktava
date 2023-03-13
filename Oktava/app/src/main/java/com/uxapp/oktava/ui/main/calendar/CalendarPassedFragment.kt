package com.uxapp.oktava.ui.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.adapters.recordings.RecordingsAdapter
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.utils.StringConverter
import kotlinx.coroutines.flow.collect
import java.util.*

class CalendarPassedFragment: Fragment() {

    private val calendarViewModel by lazy {
        (requireActivity() as MainActivity).calendarViewModel
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var stepsTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var durationTextView: TextView

    private val adapter = RecordingsAdapter(
        withImage = true,
        onDelete = { path ->
            calendarViewModel.deleteRecording(path)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_passed, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        view.apply {
            recyclerView = findViewById(R.id.recordingsOfPassedRecyclerView)
            stepsTextView = findViewById(R.id.passedStepSongsTextView)
            dateTextView = findViewById(R.id.passedDateTextView)
            durationTextView = findViewById(R.id.passedDurationTextView)
        }
        setupSessionInformation()
    }

    private fun setupSessionInformation() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            calendarViewModel.getSessionsInformationOfSelectedDayFlow().collect { sessionsInfo ->
                adapter.submitList(sessionsInfo.recordings)
                adapter.notifyDataSetChanged()
                stepsTextView.text = StringConverter.getStringSteps(sessionsInfo.stepsCount) +
                        "\nв " + StringConverter.getStringSongs(sessionsInfo.songsCount)
                dateTextView.text = StringConverter.calendarToTimeDayMonthString(sessionsInfo.day)
                durationTextView.text = StringConverter.getStringHours(sessionsInfo.wholeDuration.hours) +
                        "\n" + StringConverter.getStringMinutes(sessionsInfo.wholeDuration.minutes)
            }
        }
    }
}