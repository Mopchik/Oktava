package com.uxapp.oktava.ui.adapters.songs_with_recordings

import androidx.recyclerview.widget.DiffUtil
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel

class SongWithRecordingsDiffCallback: DiffUtil.ItemCallback<SongWithRecordingsModel>()  {
    override fun areItemsTheSame(oldItem: SongWithRecordingsModel, newItem: SongWithRecordingsModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongWithRecordingsModel, newItem: SongWithRecordingsModel): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.author == newItem.author &&
                oldItem.recordingsCount == newItem.recordingsCount &&
                oldItem.filePicture == newItem.filePicture
    }
}