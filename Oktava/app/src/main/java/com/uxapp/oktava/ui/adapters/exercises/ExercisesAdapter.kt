package com.uxapp.oktava.ui.adapters.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.uxapp.oktava.R
import java.util.*

class ExercisesAdapter(
    private val onChangingExercise: (dateOfExercise: Calendar) -> Unit,
    private val onDeletingExercise: (dateOfExercise: Calendar) -> Unit
):  ListAdapter<Calendar, ExerciseViewHolder>(ExerciseDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(
            layoutInflater.inflate(R.layout.item_exercise, parent, false),
            onChangingExercise,
            onDeletingExercise
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}