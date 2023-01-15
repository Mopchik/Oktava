package com.uxapp.oktava.audio

import android.content.Context
import android.media.MediaRecorder

class RecorderWorker(private val context: Context) {
    private var num = 0
    var isRecording = false
    private lateinit var mediaRecorder: MediaRecorder
    fun startRecording(){
        val output = context.filesDir.path + "/recording${num}.mp3"
        mediaRecorder = MediaRecorder().apply{
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(output)
            prepare()
            start()
        }
        isRecording = true
    }
   fun stopRecording(){
        mediaRecorder.stop()
        mediaRecorder.release()
        isRecording = false
        PlayerWorker(context).playRecording("recording${num}.mp3")
    }
}