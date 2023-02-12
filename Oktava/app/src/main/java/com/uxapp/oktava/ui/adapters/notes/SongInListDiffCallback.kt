package com.uxapp.oktava.ui.adapters.notes

import androidx.recyclerview.widget.DiffUtil
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel

class SongInListDiffCallback: DiffUtil.ItemCallback<SongInListModel>()  {
    override fun areItemsTheSame(oldItem: SongInListModel, newItem: SongInListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongInListModel, newItem: SongInListModel): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.author == newItem.author &&
                oldItem.lastStep == newItem.lastStep &&
                oldItem.filePicture == newItem.filePicture
    }
}