package com.uxapp.oktava.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.utils.setTextIfNotEquals

class WordsNotesFragment : Fragment() {

    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    private lateinit var wordsEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_words_creates_notes, container, false)
        wordsEditText = view.findViewById(R.id.wordsCreatesNotesEditText)
        setupEditText()
        return view
    }

    private fun setupEditText() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            songsViewModel.songsCreationProcess.getSongWordsFlow().collect {
                wordsEditText.setTextIfNotEquals(it)
            }
        }
        wordsEditText.addTextChangedListener {
            songsViewModel.songsCreationProcess.setWords(it.toString())
        }
    }
}