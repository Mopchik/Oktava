package com.uxapp.oktava.ui.main.recordings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.uxapp.oktava.R
import com.uxapp.oktava.application.App
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.main.notes.AboutNotesFragment
import com.uxapp.oktava.ui.main.notes.NotesOfNotesFragment
import com.uxapp.oktava.ui.main.notes.WordsNotesFragment
import com.uxapp.oktava.viewmodels.RecordingsViewModel

class RecordingsFragment: Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var buttonBack: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recordings, container, false)
        view.apply {
            tabLayout = findViewById(R.id.recordingsTabLayout)
            buttonBack = findViewById(R.id.recordingsBackButton)
        }
        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        initTabLayout()
        childFragmentManager.beginTransaction()
            .replace(R.id.recordingsFragmentContainer, RecordingsByDateFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        return view
    }

    private fun initTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                val fragmentOfCreation = when (tab.position) {
                    0 -> RecordingsByDateFragment()
                    1 -> RecordingsBySongFragment()
                    else -> NotesOfNotesFragment()
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.recordingsFragmentContainer, fragmentOfCreation)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}