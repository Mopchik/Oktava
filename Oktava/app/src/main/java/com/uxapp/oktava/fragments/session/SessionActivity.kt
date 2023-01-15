package com.uxapp.oktava.fragments.session


import android.os.Bundle
import android.widget.Button
import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uxapp.oktava.R
import com.uxapp.oktava.audio.RecorderWorker

class SessionActivity: Activity() {
    private lateinit var recordVoiceButton: Button
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val recorder by lazy{
        RecorderWorker(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_session)
        recordVoiceButton = findViewById<Button>(R.id.buttonRecordVoice)
        recordVoiceButton.setOnClickListener{onRecordVoiceButtonClicked()}
        val endSessionButton = findViewById<Button>(R.id.buttonEndSession)
        endSessionButton.setOnClickListener { super.onBackPressed() }
        // permissionLauncher = registerForActivityResult(
        //     ActivityResultContracts.RequestPermission()
        // ) { // isGranted ->
        // if (isGranted) {
        //     // Do if the permission is granted
        // }
        // else {
        //     // Do otherwise
        // }
        // }
    }
    // override fun onCreateView(
    //     inflater: LayoutInflater,
    //     container: ViewGroup?,
    //     savedInstanceState: Bundle?
    // ): View? {
    //     val view = inflater.inflate(R.layout.fragment_session, container, false)
    //     recordVoiceButton = view.findViewById<Button>(R.id.buttonRecordVoice)
    //     recordVoiceButton.setOnClickListener{onRecordVoiceButtonClicked()}
    //     return view
    // }
    override fun onBackPressed(){
        Toast
            .makeText(this, "Завершите занятие, чтобы выйти назад!", Toast.LENGTH_LONG)
            .show()
    }
    private fun onRecordVoiceButtonClicked(){
        if(recorder.isRecording) {
            recorder.stopRecording()
            recordVoiceButton.text = "Начать запись"
        }
        else {
            if(requestPermissions()) {
                recorder.startRecording()
                recordVoiceButton.text = "Остановить запись"
            }
        }
    }
    private fun requestPermissions(): Boolean{
        ActivityCompat.requestPermissions(this, arrayOf(RECORD_AUDIO), 1)
        // permissionLauncher.launch(RECORD_AUDIO)
        return ContextCompat.checkSelfPermission(this, RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED
    }
}