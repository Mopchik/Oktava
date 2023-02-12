package com.uxapp.oktava.ui.adapters.recordings

import androidx.recyclerview.widget.DiffUtil
import androidx.versionedparcelable.R
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel

class RecordingsDiffCallback: DiffUtil.ItemCallback<RecordingModel>()  {
    override fun areItemsTheSame(oldItem: RecordingModel, newItem: RecordingModel): Boolean {
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: RecordingModel, newItem: RecordingModel): Boolean {
        return oldItem == newItem
    }
}