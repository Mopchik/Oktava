package com.uxapp.oktava.ui.main.notes

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.adapters.notes.SongsAdapter
import com.uxapp.oktava.ui.main.MainActivity

class NotesFragment : Fragment() {
    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    private lateinit var notesBackButton: ImageButton
    private lateinit var createCardButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songsViewModel.songsChooseProcess.notChosen()
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        setupViews(view)
        setupButtons()
        childFragmentManager.beginTransaction()
            .replace(R.id.songsContainer, NotesSongsFragment())
            .commit()
        return view
    }

    private fun setupViews(view: View) {
        notesBackButton = view.findViewById(R.id.notesBackButton)
        createCardButton = view.findViewById(R.id.createCardButton)
    }

    private fun setupButtons(){
        notesBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        createCardButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, CreateAndEditNotesFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
