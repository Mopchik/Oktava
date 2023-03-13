package com.uxapp.oktava.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.uxapp.oktava.R
import com.uxapp.oktava.storage.model.Song
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.main.models.SongAboutModel
import com.uxapp.oktava.utils.Genre
import com.uxapp.oktava.utils.setupTextViewNotEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateAndEditNotesFragment : Fragment() {

    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    private lateinit var tabLayout: TabLayout
    private lateinit var buttonSave: Button
    private lateinit var buttonBack: ImageButton
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            songsViewModel.startCreationOrEdition()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_newsong, container, false)
        tabLayout = view.findViewById(R.id.createNotesTabLayout)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonBack = view.findViewById(R.id.createNotesBackButton)
        titleTextView = view.findViewById(R.id.notesCreateTitleTextView)
        init()
        return view
    }

    private fun init() {
        titleTextView.text = if(songsViewModel.songsChooseProcess.isChosen()) {
            "Редактировать"
        } else {
            "Создать карточку"
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.notesFragmentContainer, AboutNotesFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
        initTabLayout()
        initButtons()
    }

    private fun initButtons(){
        buttonSave.setOnClickListener {
            songsViewModel.songsCreationProcess.finish()
            requireActivity().onBackPressed()
        }
        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                val fragmentOfCreation = when (tab.position) {
                    0 -> AboutNotesFragment()
                    1 -> WordsNotesFragment()
                    else -> NotesOfNotesFragment()
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.notesFragmentContainer, fragmentOfCreation)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}