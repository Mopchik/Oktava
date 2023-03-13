package com.uxapp.oktava.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ListAdapter
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.adapters.notes.SongInListViewHolder
import com.uxapp.oktava.ui.adapters.notes.SongsAdapter
import com.uxapp.oktava.ui.main.BaseSongsFragment
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel

class NotesSongsFragment: BaseSongsFragment<SongInListModel>() {

    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songsViewModel.songsChooseProcess.notChosen()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getAdapter(): ListAdapter<SongInListModel, SongInListViewHolder> {
        return SongsAdapter(::onSongItemClicked)
    }

    private fun onSongItemClicked(songId: Int) {
        songsViewModel.songsChooseProcess.choose(songId)
        requireActivity().supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragment_container, SongCardFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun getCompletedSongsFlow() = songsViewModel.getCompletedSongsFlow()

    override fun getInProgressSongsFlow() = songsViewModel.getInProgressSongsFlow()

    override fun getNotStartedSongsFlow() = songsViewModel.getNotStartedSongsFlow()
}