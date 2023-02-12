package com.uxapp.oktava.ui.myViews

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.updateMargins
import com.uxapp.oktava.R
import com.uxapp.oktava.utils.dpToPx


class ListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val leadFrame: FrameLayout
    private val title: TextView
    init {
        LayoutInflater.from(context).inflate(R.layout.abstract_list_item, this)
        leadFrame = findViewById(R.id.leadFrame)
        title = findViewById(R.id.textListItem)
    }

    fun setText(text: String?) {
        title.text = text
    }

    fun setBottomSpace(exist: Boolean){
        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        if(exist) {
            params.updateMargins(bottom = dpToPx(10f))
        } else {
            params.updateMargins(bottom = dpToPx(0f))
        }
        layoutParams = params
    }

    fun setLeadView(view: View){
        leadFrame.addView(view)
        // leadFrame.invalidate()
    }
}