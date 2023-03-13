package com.uxapp.oktava.ui.main.recordings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.adapters.notes.SongsAdapter
import com.uxapp.oktava.ui.adapters.recordings.RecordingsAdapter
import com.uxapp.oktava.ui.main.MainActivity

class RecordingsByDateFragment: Fragment() {

    private val recordingsViewModel by lazy {
        (requireActivity() as MainActivity).recordingsViewModel
    }

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var beforeRecyclerView: RecyclerView
    private lateinit var yesterdayRecyclerView: RecyclerView
    private lateinit var weekRecyclerView: RecyclerView
    private lateinit var monthRecyclerView: RecyclerView

    private lateinit var todayTextView: TextView
    private lateinit var beforeTextView: TextView
    private lateinit var yesterdayTextView: TextView
    private lateinit var weekTextView: TextView
    private lateinit var monthTextView: TextView

    private lateinit var bubbleRecordings: ImageView

    private val todayAdapter = RecordingsAdapter(
        withImage = true
    ) {
        recordingsViewModel.deleteRecording(it)
    }
    private val beforeAdapter = RecordingsAdapter(
        withImage = true
    ) {
        recordingsViewModel.deleteRecording(it)
    }
    private val yesterdayAdapter = RecordingsAdapter(
        withImage = true
    ) {
        recordingsViewModel.deleteRecording(it)
    }
    private val weekAdapter = RecordingsAdapter(
        withImage = true
    ) {
        recordingsViewModel.deleteRecording(it)
    }
    private val monthAdapter = RecordingsAdapter(
        withImage = true
    ) {
        recordingsViewModel.deleteRecording(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recordings_by_date, container, false)
        setupViews(view)
        setupRecyclers()
        setupBubble()
        return view
    }

    private fun setupViews(view: View) {
        view.apply {
            todayRecyclerView = findViewById(R.id.recordingsTodayRecyclerView)
            yesterdayRecyclerView = findViewById(R.id.recordingsYesterdayRecyclerView)
            weekRecyclerView = findViewById(R.id.recordingsThisWeekRecyclerView)
            monthRecyclerView = findViewById(R.id.recordingsThisMonthRecyclerView)
            beforeRecyclerView = findViewById(R.id.recordingsBeforeRecyclerView)

            todayTextView = findViewById(R.id.textViewRecordingsToday)
            yesterdayTextView = findViewById(R.id.textViewRecordingsYesterday)
            weekTextView = findViewById(R.id.textViewRecordingsThisWeek)
            monthTextView = findViewById(R.id.textViewRecordingsThisMonth)
            beforeTextView = findViewById(R.id.textViewRecordingsBefore)

            bubbleRecordings = findViewById(R.id.bubbleRecordingsImageView)
        }
    }

    private fun setupRecyclers() {
        todayRecyclerView.adapter = todayAdapter
        todayRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        yesterdayRecyclerView.adapter = yesterdayAdapter
        yesterdayRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        weekRecyclerView.adapter = weekAdapter
        weekRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        monthRecyclerView.adapter = monthAdapter
        monthRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        beforeRecyclerView.adapter = beforeAdapter
        beforeRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        observeData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            recordingsViewModel.getTodayRecordingsFlow().collect {
                todayTextView.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
                todayAdapter.submitList(it)
                todayAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            recordingsViewModel.getYesterdayRecordingsFlow().collect {
                yesterdayTextView.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
                yesterdayAdapter.submitList(it)
                yesterdayAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            recordingsViewModel.getWeekRecordingsFlow().collect {
                weekTextView.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
                weekAdapter.submitList(it)
                weekAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            recordingsViewModel.getMonthRecordingsFlow().collect {
                monthTextView.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
                monthAdapter.submitList(it)
                monthAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            recordingsViewModel.getBeforeRecordingsFlow().collect {
                beforeTextView.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
                beforeAdapter.submitList(it)
                beforeAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupBubble(){
        val prefs = (requireActivity() as MainActivity).bubblePrefs
        if(prefs.getBoolean("isFirstRecordings", true)) {
            bubbleRecordings.visibility = View.VISIBLE
            prefs.edit().putBoolean("isFirstRecordings", false).apply()
        } else {
            bubbleRecordings.visibility = View.GONE
        }
    }
}