package com.uxapp.oktava.ui.adapters.recordings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.audio.PlayerWorker
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel

abstract class RecordingsPlayerBaseViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    open fun bind(item: RecordingModel) {
        val playerWorker = PlayerWorker(itemView.context, item.path)

        itemView.setOnClickListener {
            if (playerWorker.isPlaying()) {
                playerWorker.pause()
            } else {
                playerWorker.playRecording()
            }
        }
    }
}