package com.uxapp.oktava.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.notes.NotesFragment
import com.uxapp.oktava.ui.main.recordings.RecordingsFragment
import com.uxapp.oktava.ui.session.SessionActivity

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
        return fragmentView
    }
}