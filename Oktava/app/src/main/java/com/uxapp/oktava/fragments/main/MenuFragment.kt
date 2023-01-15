package com.uxapp.oktava.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.uxapp.oktava.R

class MenuFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_menu, container, false)
        val buttonBeginTraining = fragmentView.findViewById<Button>(R.id.buttonBeginTraining)
        val buttonNotes = fragmentView.findViewById<Button>(R.id.buttonNotes)
        val buttonRecordings = fragmentView.findViewById<Button>(R.id.buttonRecordings)
        buttonBeginTraining.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, BeginTrainingFragment())
                .addToBackStack(null)
                .commit()
        }
        buttonNotes.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NotesFragment())
                .addToBackStack(null)
                .commit()
        }
        buttonRecordings.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, RecordingsFragment())
                .addToBackStack(null)
                .commit()
        }
        return fragmentView
    }
}