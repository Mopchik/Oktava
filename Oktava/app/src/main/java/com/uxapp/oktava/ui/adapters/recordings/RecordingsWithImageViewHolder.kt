package com.uxapp.oktava.ui.adapters.recordings

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.session.SessionActivity
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel

class RecordingsWithImageViewHolder(
    itemView: View,
    private val onDelete: (recordingPath: String) -> Unit
) : RecordingsPlayerBaseViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.nameRecordingImageTextView)
    private val dateTextView = itemView.findViewById<TextView>(R.id.dateRecordingImageTextView)
    private val stepTextView = itemView.findViewById<TextView>(R.id.stepRecordingImageTextView)
    private val durationTextView =
        itemView.findViewById<TextView>(R.id.durationRecordingImageTextView)
    private val descriptionTextView =
        itemView.findViewById<TextView>(R.id.descriptionRecordingImageTextView)
    private val recordingImageView = itemView.findViewById<ImageView>(R.id.imageRecordingImageView)
    private val deleteImageButton =
        itemView.findViewById<ImageButton>(R.id.deleteSongRecordingImageButton)
    private val playImageView = itemView.findViewById<ImageView>(R.id.playImageRecordingImageView)
    private val pauseImageView = itemView.findViewById<ImageView>(R.id.pauseImageRecordingImageView)

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
        if (itemView.context is MainActivity && (itemView.context as MainActivity).requestPermissions() ||
                itemView.context is SessionActivity && (itemView.context as SessionActivity).requestPermissions()) {
            val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
                item.filePicture, recordingImageView.layoutParams.width
            ) ?: return
            recordingImageView.setImageBitmap(imageBitmap)
        }
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