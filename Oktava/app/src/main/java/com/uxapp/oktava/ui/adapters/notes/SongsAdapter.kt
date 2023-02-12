package com.uxapp.oktava.ui.adapters.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.uxapp.oktava.R
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel

class SongsAdapter(
    val onItemClicked: (songId: Int)->Unit
):  ListAdapter<SongInListModel, SongInListViewHolder>(SongInListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongInListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SongInListViewHolder(
            layoutInflater.inflate(R.layout.item_spiska, parent, false),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: SongInListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}