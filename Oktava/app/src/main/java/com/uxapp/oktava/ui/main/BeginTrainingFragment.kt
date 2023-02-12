package com.uxapp.oktava.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.application.App
import com.uxapp.oktava.ui.adapters.notes.SongsAdapter
import com.uxapp.oktava.ui.adapters.session.SongSessionAdapter
import com.uxapp.oktava.ui.session.SessionActivity
import com.uxapp.oktava.viewmodels.SessionViewModel


class BeginTrainingFragment: Fragment() {

    private val sessionViewModel by lazy {
        App.get(requireContext()).viewModelFactory.create(SessionViewModel::class.java)
    }

    private lateinit var backImageButton: ImageButton
    private lateinit var songsRecyclerView: RecyclerView
    private val songSessionAdapter = SongSessionAdapter(::onSongChosen)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_session_what_to_play, container, false)
        view.apply {
            backImageButton = findViewById(R.id.startSessionWhatToPlayBackButton)
            songsRecyclerView = findViewById(R.id.songsStartWhatToPlayRecyclerView)
        }
        backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        initRecyclerView()
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(){
        songsRecyclerView.adapter = songSessionAdapter
        songsRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sessionViewModel.getSongSessionModelsFlow().collect {
                songSessionAdapter.submitList(it)
                songSessionAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onSongChosen(songId: Int){
        sessionViewModel.songsChooseProcess.choose(songId)
        sessionViewModel.startSession()
        val intent = Intent(activity, SessionActivity::class.java)
        startActivity(intent)
    }
}