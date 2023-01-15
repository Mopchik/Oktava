package com.uxapp.oktava.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

class PlayerWorker(private val context: Context) {
    private lateinit var mediaPlayer: MediaPlayer

    private val audioAttributes by lazy{
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    }
    fun playRecording(recordingName: String){
        val path = Uri.parse(context.filesDir.path +
                "/" + recordingName)
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(audioAttributes)
            setDataSource(context, path)
        }
        mediaPlayer.prepare()
        mediaPlayer.start()
    }
}