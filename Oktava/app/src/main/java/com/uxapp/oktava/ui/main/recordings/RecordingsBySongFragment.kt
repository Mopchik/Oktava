package com.uxapp.oktava.ui.main.recordings

import com.uxapp.oktava.ui.adapters.songs_with_recordings.SongsWithRecordingsAdapter
import com.uxapp.oktava.ui.main.BaseSongsFragment
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel

class RecordingsBySongFragment: BaseSongsFragment<SongWithRecordingsModel>() {

    private val recordingsViewModel by lazy {
        (requireActivity() as MainActivity).recordingsViewModel
    }

    override fun getAdapter() = SongsWithRecordingsAdapter(recordingsViewModel)

    override fun getCompletedSongsFlow() = recordingsViewModel.getCompletedSongsWithRecordingsFlow()

    override fun getInProgressSongsFlow() = recordingsViewModel.getInProgressSongsWithRecordingsFlow()

    override fun getNotStartedSongsFlow() = recordingsViewModel.getNotStartedSongsWithRecordingsFlow()
}