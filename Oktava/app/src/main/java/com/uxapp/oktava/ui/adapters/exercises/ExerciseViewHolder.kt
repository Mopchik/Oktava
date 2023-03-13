package com.uxapp.oktava.ui.adapters.exercises

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.utils.getDayOfWeek
import java.util.*

class ExerciseViewHolder(
    itemView: View,
    private val onChangingExercise: (dateOfExercise: Calendar) -> Unit,
    private val onDeletingExercise: (dateOfExercise: Calendar) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val dayOfWeekTextView = itemView.findViewById<TextView>(R.id.dayOfWeekExerciseTextView)
    private val dateTextView = itemView.findViewById<TextView>(R.id.exerciseDateTextView)
    private val timeTextView = itemView.findViewById<TextView>(R.id.exerciseTimeTextView)
    private val editImageView = itemView.findViewById<ImageView>(R.id.editExerciseImageView)
    private val deleteImageView = itemView.findViewById<ImageView>(R.id.deleteExerciseImageView)

    fun bind(item: Calendar) {
        dateTextView.text = StringConverter.calendarToDayMonthString(item)
        timeTextView.text =
            StringConverter.intsToTime(item.get(Calendar.HOUR_OF_DAY), item.get(Calendar.MINUTE))
        dayOfWeekTextView.text = item.getDayOfWeek().toString()

        deleteImageView.setOnClickListener {
            onDeletingExercise(item)
        }

        editImageView.setOnClickListener {
            onChangingExercise(item)
        }
    }
}