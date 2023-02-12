package com.uxapp.oktava.ui.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import com.uxapp.oktava.utils.setupImageViewFromFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionNotesFragment: Fragment() {

    private val sessionViewModel by lazy {
        (requireActivity() as SessionActivity).sessionViewModel
    }

    private lateinit var notesImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(
            R.layout.fragment_session_notes,
            container,
            false
        )
        notesImageView = fragmentView.findViewById(R.id.notesSessionImageView)
        setupSongCardInfo()
        // setupViews(fragmentView)
        return fragmentView
    }

    private fun setupSongCardInfo() {
        lifecycleScope.launch(Dispatchers.Main) {
            val chosenSongId = sessionViewModel.songsChooseProcess.getChosenSongId()
            val songSessionModel = sessionViewModel.getSessionModelOfSong(chosenSongId) ?: return@launch
            notesImageView.setupImageViewFromFile(songSessionModel.fileNotes)
        }
    }
}