package com.uxapp.oktava.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

class PlayerWorker(private val context: Context, private val recordingName: String) {
    private val mediaPlayer: MediaPlayer by lazy {
        val path = Uri.parse(
            context.filesDir.path +
                    "/" + recordingName + ".mp3"
        )
        MediaPlayer().apply {
            setAudioAttributes(audioAttributes)
            setDataSource(context, path)
            prepareAsync()
        }
    }

    private val audioAttributes by lazy {
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    }

    fun isPlaying(): Boolean = mediaPlayer.isPlaying

    fun playRecording() {
        mediaPlayer.start()
    }

    fun setOnCompletionListener(listener: MediaPlayer.OnCompletionListener) {
        mediaPlayer.setOnCompletionListener(listener)
    }

    fun pause() = mediaPlayer.pause()

    fun resume() = mediaPlayer.start()

    fun stop() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}
