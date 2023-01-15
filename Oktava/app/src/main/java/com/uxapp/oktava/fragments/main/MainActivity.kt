package com.uxapp.oktava.fragments.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uxapp.oktava.R

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            val menuFragment = MenuFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .commit()
        }
    }
}