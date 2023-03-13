package com.uxapp.oktava.ui.adapters.songs_with_recordings

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.ui.adapters.recordings.RecordingsAdapter
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.main.models.SongWithRecordingsModel
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.viewmodels.RecordingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SongWithRecordingsViewHolder(
    itemView: View,
    private val recordingsViewModel: RecordingsViewModel
) : RecyclerView.ViewHolder(itemView) {

    private val songImageView = itemView.findViewById<ImageView>(R.id.songWithRecordingsImageView)
    private val titleTextView = itemView.findViewById<TextView>(R.id.titleSongWithRecordingsTextView)
    private val authorTextView =
        itemView.findViewById<TextView>(R.id.authorSongWithRecordingsTextView)
    private val recordingsCountTextView = itemView.findViewById<TextView>(R.id.recordingsCountTextView)
    private val recordingsRecyclerView = itemView.findViewById<RecyclerView>(R.id.recordingsOfSongRecyclerView)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val hideImageView = itemView.findViewById<ImageView>(R.id.hideRecordingsOfSongImageView)
    private val showImageView = itemView.findViewById<ImageView>(R.id.showRecordingsOfSongImageView)

    private val recordingsAdapter = RecordingsAdapter(
        withImage = false,
        onDelete = recordingsViewModel::deleteRecording
    )

    fun bind(item: SongWithRecordingsModel) {
        titleTextView.text = item.name
        authorTextView.text = item.author
        recordingsCountTextView.text = StringConverter.recordingsStringByCount(item.recordingsCount)
        setupRecyclerView(item.id)
        itemView.setOnClickListener {
            val isRecordingListVisible = hideImageView.visibility == View.VISIBLE
            if(isRecordingListVisible){
                hideImageView.visibility = View.GONE
                showImageView.visibility = View.VISIBLE
                recordingsRecyclerView.visibility = View.GONE
            } else {
                showImageView.visibility = View.GONE
                hideImageView.visibility = View.VISIBLE
                recordingsRecyclerView.visibility = View.VISIBLE
            }
        }
        if((itemView.context as MainActivity).requestPermissions()) {
            val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
                item.filePicture, songImageView.layoutParams.width
            ) ?: return
            songImageView.setImageBitmap(imageBitmap)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupRecyclerView(idOfSong: Int){
        recordingsRecyclerView.adapter = recordingsAdapter
        recordingsRecyclerView.layoutManager =
            LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        coroutineScope.launch {
            recordingsViewModel.getRecordingsOfSongFlow(idOfSong).collect {
                recordingsAdapter.submitList(it)
                recordingsAdapter.notifyDataSetChanged()
            }
        }
    }
}