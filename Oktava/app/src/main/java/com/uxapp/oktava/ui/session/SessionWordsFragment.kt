package com.uxapp.oktava.ui.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionWordsFragment: Fragment() {

    private val sessionViewModel by lazy {
        (requireActivity() as SessionActivity).sessionViewModel
    }

    private lateinit var wordsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(
            R.layout.fragment_session_words,
            container,
            false
        )
        wordsTextView = fragmentView.findViewById(R.id.wordsSessionTextView)
        setupSongCardInfo()
        // setupViews(fragmentView)
        return fragmentView
    }

    private fun setupSongCardInfo() {
        lifecycleScope.launch(Dispatchers.Main) {
            val chosenSongId = sessionViewModel.songsChooseProcess.getChosenSongId()
            val songSessionModel = sessionViewModel.getSessionModelOfSong(chosenSongId) ?: return@launch
            wordsTextView.text = songSessionModel.words
        }
    }
}