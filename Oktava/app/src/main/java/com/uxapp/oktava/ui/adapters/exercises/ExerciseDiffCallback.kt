package com.uxapp.oktava.ui.adapters.exercises

import androidx.recyclerview.widget.DiffUtil
import com.uxapp.oktava.utils.datesTimesEquals
import java.util.*

class ExerciseDiffCallback: DiffUtil.ItemCallback<Calendar>() {
    override fun areItemsTheSame(oldItem: Calendar, newItem: Calendar): Boolean {
        return oldItem.datesTimesEquals(newItem)
    }

    override fun areContentsTheSame(oldItem: Calendar, newItem: Calendar): Boolean {
        return oldItem.datesTimesEquals(newItem)
    }
}