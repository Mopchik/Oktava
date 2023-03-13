package com.uxapp.oktava.ui.adapters.recordings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.uxapp.oktava.R
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel

class RecordingsAdapter(
    private val withImage: Boolean,
    private val onDelete: (recordingPath: String) -> Unit
    // private val onItemClicked: (recordingPath: String)->Unit
):  ListAdapter<RecordingModel, RecordingsPlayerBaseViewHolder>(RecordingsDiffCallback()) {

    private var currentPlayedHolder: RecordingsPlayerBaseViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingsPlayerBaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if(!withImage){
            RecordingsViewHolder(
                layoutInflater.inflate(R.layout.item_recording, parent, false),
                onDelete
                // onItemClicked
            )
        } else {
            RecordingsWithImageViewHolder(
                layoutInflater.inflate(R.layout.item_recording_with_image, parent, false),
                onDelete
            )
        }
    }

    override fun onBindViewHolder(holder: RecordingsPlayerBaseViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.setOnClickAction {
            if(currentPlayedHolder != holder) {
                try {
                    currentPlayedHolder?.removePlayPauseIcons()
                    currentPlayedHolder?.stopPlaying()
                } catch (e: Exception) {
                }
                currentPlayedHolder = holder
            }
        }
    }
}