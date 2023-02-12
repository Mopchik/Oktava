package com.uxapp.oktava.ui.adapters.session

import androidx.recyclerview.widget.DiffUtil
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel

class SongSessionDiffCallback: DiffUtil.ItemCallback<SongSessionModel>()  {
    override fun areItemsTheSame(oldItem: SongSessionModel, newItem: SongSessionModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongSessionModel, newItem: SongSessionModel): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.step == newItem.step &&
                oldItem.filePicture == newItem.filePicture
    }
}