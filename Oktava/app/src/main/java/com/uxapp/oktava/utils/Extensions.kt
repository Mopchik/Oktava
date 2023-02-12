package com.uxapp.oktava.utils

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.uxapp.oktava.file_worker.image.ImageReader
import java.util.*


fun TextView.setupTextViewNotEmpty(
    text: String,
    titleTextView: TextView? = null
) {
    if (text == "") {
        this.visibility = View.GONE
        titleTextView?.visibility = View.GONE
    } else {
        this.text = text
    }
}

fun ImageView.setupImageViewFromFile(filePath: String?) {
    val width = if (width > 0) width else layoutParams.width
    val imageBitmap = ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
        filePath, width
    ) ?: return
    setImageBitmap(imageBitmap)
}

// fun Calendar.datesEquals(calendar: Calendar): Boolean {
//
// }

fun View.dpToPx(dp: Float): Int {
    val r: Resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.displayMetrics
    ).toInt()
}

fun Calendar.getPeriodOfDate(): Period {
    val dif = Calendar.getInstance().timeInMillis - timeInMillis
    val difDay = dif / (1000.0 * 60 * 60 * 24)
    return if(difDay <= 1) {
        Period.TODAY
    } else if(difDay <= 2) {
        Period.YESTERDAY
    } else if(difDay <= 7) {
        Period.THIS_WEEK
    } else if(difDay <= 31) {
        Period.THIS_MONTH
    } else {
        Period.BEFORE
    }
}