package com.uxapp.oktava.ui.adapters.recordings

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.viewmodels.dataModels.RecordingModel

class RecordingsWithImageViewHolder(
    itemView: View,
    private val onDelete: (recordingPath: String) -> Unit
): RecordingsPlayerBaseViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.nameRecordingImageTextView)
    private val dateTextView = itemView.findViewById<TextView>(R.id.dateRecordingImageTextView)
    private val stepTextView = itemView.findViewById<TextView>(R.id.stepRecordingImageTextView)
    private val durationTextView = itemView.findViewById<TextView>(R.id.durationRecordingImageTextView)
    private val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionRecordingImageTextView)
    private val recordingImageView = itemView.findViewById<ImageView>(R.id.imageRecordingImageView)
    private val deleteImageButton = itemView.findViewById<ImageButton>(R.id.deleteSongRecordingImageButton)

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
        val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
            item.filePicture, recordingImageView.layoutParams.width
        ) ?: return
        recordingImageView.setImageBitmap(imageBitmap)
    }

}