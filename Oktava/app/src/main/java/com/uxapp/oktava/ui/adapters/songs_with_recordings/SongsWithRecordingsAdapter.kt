package com.uxapp.oktava.ui.adapters.songs_with_recordings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel
import com.uxapp.oktava.viewmodels.RecordingsViewModel
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel

class SongsWithRecordingsAdapter(
    private val recordingsViewModel: RecordingsViewModel
):  ListAdapter<SongWithRecordingsModel, SongWithRecordingsViewHolder>(SongWithRecordingsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongWithRecordingsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SongWithRecordingsViewHolder(
            layoutInflater.inflate(R.layout.item_song_recordings, parent, false),
            recordingsViewModel
        )
    }

    override fun onBindViewHolder(holder: SongWithRecordingsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}