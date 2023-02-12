package com.uxapp.oktava.ui.adapters.notes

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.viewmodels.dataModels.SongInListModel


class SongInListViewHolder(
    itemView: View,
    private val onItemClicked: (songId: Int) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    private val songListImageView = itemView.findViewById<ImageView>(R.id.songListImageView)
    private val titleSongListTextView = itemView.findViewById<TextView>(R.id.titleSongListTextView)
    private val authorSongListTextView =
        itemView.findViewById<TextView>(R.id.authorSongListTextView)
    private val stepTextView = itemView.findViewById<TextView>(R.id.stepTextView)

    fun bind(item: SongInListModel) {
        titleSongListTextView.text = item.name
        authorSongListTextView.text = item.author
        stepTextView.text = item.lastStep.toString()
        itemView.setOnClickListener {
            onItemClicked(item.id)
        }
        val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
            item.filePicture, songListImageView.layoutParams.width
        ) ?: return
        songListImageView.setImageBitmap(imageBitmap)
    }
}