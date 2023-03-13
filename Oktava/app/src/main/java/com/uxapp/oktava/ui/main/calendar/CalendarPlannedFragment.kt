package com.uxapp.oktava.ui.main.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.adapters.exercises.ExercisesAdapter
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.utils.getMyTime
import kotlinx.coroutines.flow.collect
import java.util.*

class CalendarPlannedFragment: Fragment() {

    private val calendarViewModel by lazy {
        (requireActivity() as MainActivity).calendarViewModel
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var addNew: View
    private val adapter = ExercisesAdapter(
        onChangingExercise = this::onChangingExercise,
        onDeletingExercise = this::onDeletingExercise
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_planned, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        view.apply {
            recyclerView = findViewById(R.id.exercisesRecyclerView)
            addNew = findViewById(R.id.addNewExerciseContainer)
        }
        setupRecycler()
        setupAddNew()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecycler() {
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            calendarViewModel.getPlannedTimesOfSelectedDay().collect {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupAddNew() {
        addNew.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.calendarFragmentContainer, CalendarEditFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun onChangingExercise(exerciseDate: Calendar) {
        calendarViewModel.changeDateStart(exerciseDate)
        parentFragmentManager.beginTransaction()
            .replace(R.id.calendarFragmentContainer, CalendarEditFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun onDeletingExercise(exerciseDate: Calendar) {
        calendarViewModel.deleteOneTraining(exerciseDate.getMyTime())
    }
}