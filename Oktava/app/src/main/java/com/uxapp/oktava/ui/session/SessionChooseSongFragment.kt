package com.uxapp.oktava.ui.session

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.application.App
import com.uxapp.oktava.audio.PlayerWorker
import com.uxapp.oktava.ui.adapters.recordings.RecordingsAdapter
import com.uxapp.oktava.ui.adapters.session.SongSessionAdapter
import com.uxapp.oktava.viewmodels.SessionViewModel

class SessionChooseSongFragment(val mode: Mode) : Fragment() {

    private val sessionViewModel by lazy {
        (requireActivity() as SessionActivity).sessionViewModel
    }

    private lateinit var endSessionButton: Button
    private lateinit var backButton: AppCompatImageButton
    private lateinit var songsRecyclerView: RecyclerView
    private lateinit var todayRecordingsTextView: TextView
    private lateinit var todayRecordingsRecyclerView: RecyclerView

    private val songSessionAdapter = SongSessionAdapter(::onSongChosen)
    private val recordingsAdapter = RecordingsAdapter(
        withImage = true
    ) {
        sessionViewModel.deleteRecording(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(
            R.layout.fragment_session_what_to_play,
            container,
            false
        )
        setupViews(fragmentView)
        return fragmentView
    }

    private fun setupViews(view: View) {
        view.apply {
            endSessionButton = findViewById(R.id.endSessionButton)
            backButton = findViewById(R.id.sessionWhatToPlayBackButton)
            songsRecyclerView = findViewById(R.id.songsWhatToPlayRecyclerView)
            todayRecordingsTextView = findViewById(R.id.todayRecordingsTextView)
            todayRecordingsRecyclerView = findViewById(R.id.recordingsWhatToPlayRecyclerView)
        }
        applyMode()
        initRecyclersView()
        initButtons()
    }

    private fun applyMode() {
        when (mode) {
            Mode.START_SESSION -> {
                todayRecordingsTextView.visibility = View.GONE
                todayRecordingsRecyclerView.visibility = View.GONE
                endSessionButton.visibility = View.GONE
                backButton.visibility = View.VISIBLE
            }
            Mode.CHOOSE_NEW_SONG -> {
                todayRecordingsTextView.visibility = View.VISIBLE
                todayRecordingsRecyclerView.visibility = View.VISIBLE
                endSessionButton.visibility = View.VISIBLE
                backButton.visibility = View.GONE
            }
        }
    }

    private fun initButtons() {
        endSessionButton.setOnClickListener {
            sessionViewModel.endSession()
            (requireActivity() as SessionActivity).back()
        }
        backButton.setOnClickListener {
            (requireActivity() as SessionActivity).back()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclersView() {
        songsRecyclerView.adapter = songSessionAdapter
        songsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sessionViewModel.getSongSessionModelsFlow().collect {
                songSessionAdapter.submitList(it)
                songSessionAdapter.notifyDataSetChanged()
            }
        }
        todayRecordingsRecyclerView.adapter = recordingsAdapter
        todayRecordingsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sessionViewModel.getRecordingsOfThisSessionFlow().collect {
                recordingsAdapter.submitList(it)
                recordingsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onSongChosen(songId: Int) {
        sessionViewModel.songsChooseProcess.choose(songId)
        if (mode == Mode.START_SESSION) {
            sessionViewModel.startSession()
        }
        parentFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.session_fragment_container, SessionWorkFragment())
            .commit()
    }

    companion object {
        enum class Mode {
            START_SESSION, CHOOSE_NEW_SONG
        }
    }
}