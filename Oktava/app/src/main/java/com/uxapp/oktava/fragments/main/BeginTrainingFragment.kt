package com.uxapp.oktava.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.uxapp.oktava.R
import com.uxapp.oktava.fragments.session.SessionActivity


class BeginTrainingFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_begin_training, container, false)
        val button = view.findViewById<Button>(R.id.buttonBeginSession)
        button.setOnClickListener {
            val intent = Intent(activity, SessionActivity::class.java)
            startActivity(intent)
            // parentFragmentManager
            //     .beginTransaction()
            //     .replace(R.id.fragment_container, SessionFragment())
            //     .addToBackStack(null)
            //     .commit()
        }
        return view
    }
}