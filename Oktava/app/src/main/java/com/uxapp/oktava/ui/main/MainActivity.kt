package com.uxapp.oktava.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uxapp.oktava.R
import com.uxapp.oktava.application.App
import com.uxapp.oktava.viewmodels.RecordingsViewModel
import com.uxapp.oktava.viewmodels.SongsViewModel

class MainActivity : AppCompatActivity() {
    private val app by lazy { App.get(this) }
    val songsViewModel: SongsViewModel by viewModels {
        app.viewModelFactory
    }
    val recordingsViewModel: RecordingsViewModel by viewModels {
        app.viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            if (requestPermissions()) {
                val menuFragment = MenuFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, menuFragment)
                    .commit()
            }
        }
    }

    private fun requestPermissions(): Boolean {
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