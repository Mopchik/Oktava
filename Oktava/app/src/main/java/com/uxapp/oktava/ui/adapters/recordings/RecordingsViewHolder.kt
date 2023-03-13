package com.uxapp.oktava.ui.adapters.recordings

import android.media.Image
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.audio.PlayerWorker
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel
import java.time.format.DateTimeFormatter
import java.util.*

class RecordingsViewHolder(
    itemView: View,
    private val onDelete: (recordingPath: String) -> Unit
    // private val onItemClicked: (recordingPath: String) -> Unit,
): RecordingsPlayerBaseViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.nameRecordingTextView)
    private val dateTextView = itemView.findViewById<TextView>(R.id.dateRecordingTextView)
    private val stepTextView = itemView.findViewById<TextView>(R.id.stepRecordingTextView)
    private val durationTextView = itemView.findViewById<TextView>(R.id.durationRecordingTextView)
    private val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionRecordingTextView)
    private val deleteImageButton = itemView.findViewById<ImageButton>(R.id.deleteSongActionMenu)
    private val playImageView = itemView.findViewById<ImageView>(R.id.playRecordingImageView)
    private val pauseImageView = itemView.findViewById<ImageView>(R.id.pauseRecordingImageView)

    override fun bind(item: RecordingModel) {
        super.bind(item)
        nameTextView.text = item.name
        stepTextView.text = "Шаг ${item.step}"
        dateTextView.text = StringConverter.calendarToDayMonthString(item.date)
        durationTextView.text = StringConverter.myTimeToMinuteSecondString(item.duration)
        descriptionTextView.text = item.description
        deleteImageButton.setOnClickListener {
            onDelete(item.path)
        }
        // val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
        //     item.filePicture, songSessionImageView.layoutParams.width
        // ) ?: return
        // songSessionImageView.setImageBitmap(imageBitmap)
    }

    override fun onComplete() {
        if(playImageView.visibility != View.VISIBLE &&
            pauseImageView.visibility != View.VISIBLE) {
            return
        }
        setIcon(false)
    }

    override fun setIcon(isPlaying: Boolean) {
        if(isPlaying) {
            playImageView.visibility = View.GONE
            pauseImageView.visibility = View.VISIBLE
        } else {
            pauseImageView.visibility = View.GONE
            playImageView.visibility = View.VISIBLE
        }
    }

    override fun removePlayPauseIcons() {
        pauseImageView.visibility = View.GONE
        playImageView.visibility = View.GONE
    }

}