package com.uxapp.oktava.ui.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipGroup
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.utils.MyTime
import com.uxapp.oktava.utils.getMyTime
import java.util.*

class CalendarEditFragment: Fragment() {

    private val calendarViewModel by lazy {
        (requireActivity() as MainActivity).calendarViewModel
    }

    private lateinit var chips: ChipGroup
    private lateinit var timePicker: TimePicker
    private lateinit var saveButton: Button
    private lateinit var checkBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_edit, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View){
        view.apply {
            chips = findViewById(R.id.chips_group)
            timePicker = findViewById(R.id.timePickerEdit)
            saveButton = findViewById(R.id.saveExerciseButton)
            checkBox = findViewById(R.id.checkbox_every_week)
        }
        setupChips()
        setupTimePicker()
        setupButton()
    }

    private fun setupChips() {
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                chips.visibility = View.VISIBLE
            } else {
                chips.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupTimePicker() {
        timePicker.setIs24HourView(true)
        val oldTime = calendarViewModel.dateOfChangingExercise.getMyTime()
        timePicker.hour = oldTime.hours
        timePicker.minute = oldTime.minutes
    }

    private fun setupButton() {
        saveButton.text = if(calendarViewModel.isChangingExercise) {
            "Сохранить"
        } else {
            "Запланировать"
        }
        saveButton.setOnClickListener {
            val time = MyTime(timePicker.hour, timePicker.minute, 0)
            if(checkBox.isChecked) {

            } else {
                if(calendarViewModel.isChangingExercise) {
                    calendarViewModel.editOneTime(time)
                } else {
                    calendarViewModel.planOneNewTraining(time)
                }
            }
            parentFragmentManager.popBackStack()
        }
    }
}