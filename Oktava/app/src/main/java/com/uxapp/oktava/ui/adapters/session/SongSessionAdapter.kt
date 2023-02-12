package com.uxapp.oktava.ui.adapters.session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.uxapp.oktava.R
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel

class SongSessionAdapter(
    private val onItemClicked: (songId: Int)->Unit
):  ListAdapter<SongSessionModel, SongSessionViewHolder>(SongSessionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongSessionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SongSessionViewHolder(
            layoutInflater.inflate(R.layout.item_little_song_card, parent, false),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: SongSessionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}