package com.uxapp.oktava.ui.adapters.recordings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.audio.PlayerWorker
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel

abstract class RecordingsPlayerBaseViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private var onClickAction: (() -> Unit)? = null
    private lateinit var playerWorker: PlayerWorker

    open fun bind(item: RecordingModel) {

        playerWorker = PlayerWorker(itemView.context, item.path)

        itemView.setOnClickListener {
            onClickAction?.invoke()
            if (playerWorker.isPlaying()) {
                playerWorker.pause()
            } else {
                playerWorker.playRecording()
            }
            setIcon(playerWorker.isPlaying())
        }

        playerWorker.setOnCompletionListener {
            onComplete()
        }
    }

    fun setOnClickAction(action: () -> Unit) {
        onClickAction = action
    }

    fun stopPlaying() {
        playerWorker.pause()
    }

    abstract fun onComplete()

    abstract fun setIcon(isPlaying: Boolean)

    abstract fun removePlayPauseIcons()
}