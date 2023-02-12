package com.uxapp.oktava.audio

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import com.uxapp.oktava.utils.MyTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class RecorderWorker(
    private val context: Context,
    private val lifecycleScope: CoroutineScope
) {

    var isRecording = false

    private lateinit var mediaRecorder: MediaRecorder

    private var myTime = MyTime(0, 0, 0)
    val recordingDuration
        get() = myTime
    private val timerMutableStateFlow = MutableStateFlow(myTime)
    val timerFlow: Flow<MyTime> = timerMutableStateFlow

    fun startRecording(filePath: String) {
        val output = context.filesDir.path + "/$filePath.mp3"
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(output)
            prepare()
            start()
        }
        myTime = MyTime(0, 0, 0)

        isRecording = true

        CoroutineScope(Dispatchers.IO).launch {
            while(isRecording) {
                delay(1000)
                myTime.seconds++
                if (myTime.seconds >= 60) {
                    myTime.minutes++
                    myTime.seconds = 0
                }
                if (myTime.minutes >= 60) {
                    myTime.hours++
                    myTime.minutes = 0
                }
                timerMutableStateFlow.emit(myTime.copy())
            }
        }
    }

    fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
        isRecording = false
        // PlayerWorker(context).playRecording("recording${num}.mp3")
    }
}