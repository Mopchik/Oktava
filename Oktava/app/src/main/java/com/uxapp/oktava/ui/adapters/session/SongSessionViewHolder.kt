package com.uxapp.oktava.ui.adapters.session

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.viewmodels.dataModels.SongSessionModel

class SongSessionViewHolder(
    itemView: View,
    private val onItemClicked: (songId: Int) -> Unit,
): RecyclerView.ViewHolder(itemView) {

    private val songSessionImageView = itemView.findViewById<ImageView>(R.id.songSessionImageView)
    private val nameSessionTextView = itemView.findViewById<TextView>(R.id.songSessionName)
    private val stepSessionTextView = itemView.findViewById<TextView>(R.id.songSessionStep)

    fun bind(item: SongSessionModel) {
        nameSessionTextView.text = item.name
        stepSessionTextView.text = "Шаг ${item.step}"
        itemView.setOnClickListener {
            onItemClicked(item.id)
        }
        val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
            item.filePicture, songSessionImageView.layoutParams.width
        ) ?: return
        songSessionImageView.setImageBitmap(imageBitmap)
    }
}