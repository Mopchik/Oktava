package com.uxapp.oktava.ui.session


import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.uxapp.oktava.R
import com.uxapp.oktava.application.App
import com.uxapp.oktava.audio.RecorderWorker
import com.uxapp.oktava.ui.session.SessionChooseSongFragment.Companion.Mode
import com.uxapp.oktava.ui.main.MenuFragment
import com.uxapp.oktava.viewmodels.SessionViewModel

class SessionActivity : AppCompatActivity() {
    val sessionViewModel by lazy {
        App.get(this).viewModelFactory.create(SessionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(
                    R.id.session_fragment_container,
                    SessionChooseSongFragment(Mode.START_SESSION)
                )
                .commit()
        }
    }

    override fun onBackPressed() {
        val fragmentsStack = supportFragmentManager.fragments
        when (fragmentsStack[fragmentsStack.size - 1]) {
            is SessionChooseSongFragment -> {
                val chooseFragment =
                    fragmentsStack[fragmentsStack.size - 1] as SessionChooseSongFragment
                if (chooseFragment.mode == Mode.START_SESSION) {
                    super.onBackPressed()
                } else if (chooseFragment.mode == Mode.CHOOSE_NEW_SONG) {
                    Toast.makeText(this, "Завершите занятие, чтобы выйти назад!", Toast.LENGTH_LONG)
                        .show()
                }
            }
            is SessionWorkFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.session_fragment_container,
                        SessionChooseSongFragment(Mode.CHOOSE_NEW_SONG)
                    )
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }

    }

    fun back() {
        super.onBackPressed()
    }


    fun requestPermissions(): Boolean {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}