package com.uxapp.oktava.ui.session

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.uxapp.oktava.R
import com.uxapp.oktava.audio.RecorderWorker
import com.uxapp.oktava.storage.holders.RecordingNameHolder
import com.uxapp.oktava.ui.main.notes.AboutNotesFragment
import com.uxapp.oktava.ui.main.notes.NotesOfNotesFragment
import com.uxapp.oktava.ui.main.notes.WordsNotesFragment
import com.uxapp.oktava.utils.MyTime
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.utils.setupImageViewFromFile
import com.uxapp.oktava.utils.setupTextViewNotEmpty
import com.uxapp.oktava.viewmodels.dataModels.RecordingCreatedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionWorkFragment : Fragment() {

    private val sessionViewModel by lazy {
        (requireActivity() as SessionActivity).sessionViewModel
    }

    private lateinit var startRecordingImageView: ImageView
    private lateinit var stopRecordingImageView: ImageView
    private lateinit var backButton: ImageButton
    private lateinit var songName: TextView
    private lateinit var tabLayout: TabLayout
    private lateinit var timerTextView: TextView
    private lateinit var recordingNameTextView: TextView
    private lateinit var descriptionEditText: EditText
    private lateinit var closeRecordingImageView: ImageView
    private lateinit var submitRecordingImageView: ImageView
    private lateinit var newRecordingTextView: TextView

    private val recorderWorker by lazy {
        RecorderWorker(requireContext(), viewLifecycleOwner.lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(
            R.layout.fragment_session_in_progress,
            container,
            false
        )
        setupViews(fragmentView)
        return fragmentView
    }

    override fun onPause() {
        super.onPause()
        if (recorderWorker.isRecording) {
            recorderWorker.stopRecording()
        }
    }

    private fun setupViews(view: View) {
        view.apply {
            backButton = findViewById(R.id.sessionInProgressBackButton)
            songName = findViewById(R.id.sessionSongNameTextView)
            tabLayout = findViewById(R.id.sessionTabLayout)
            startRecordingImageView = findViewById(R.id.startRecordingImageView)
            stopRecordingImageView = findViewById(R.id.stopRecordingImageView)
            timerTextView = findViewById(R.id.recordingTimerTextView)
            recordingNameTextView = findViewById(R.id.recordingTitleTextView)
            descriptionEditText = findViewById(R.id.recordingDescriptionEditText)
            closeRecordingImageView = findViewById(R.id.notAddRecordingImageView)
            submitRecordingImageView = findViewById(R.id.submitRecordingImageView)
            newRecordingTextView = findViewById(R.id.startRecordingTextView)
        }
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setupSongCardInfo()
        setupTabLayout()
        setupRecordingButtons()
        setupRecordingTimer()
        childFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.notesFragmentContainer, SessionStepsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setupTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                val fragmentOfSession = when (tab.position) {
                    0 -> SessionStepsFragment()
                    1 -> SessionWordsFragment()
                    else -> SessionNotesFragment()
                }
                childFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.notesFragmentContainer, fragmentOfSession)
                    .addToBackStack(null)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setupRecordingTimer() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            recorderWorker.timerFlow.collect {
                timerTextView.text = StringConverter.myTimeToMinuteSecondString(it)
            }
        }
    }

    private fun setupSongCardInfo() {
        lifecycleScope.launch(Dispatchers.Main) {
            val chosenSongId = sessionViewModel.songsChooseProcess.getChosenSongId()
            val songSessionModel =
                sessionViewModel.getSessionModelOfSong(chosenSongId) ?: return@launch
            songName.text = songSessionModel.name
        }
    }

    private fun setupRecordingButtons() {
        startRecordingImageView.setOnClickListener {
            startRecording()
        }
        stopRecordingImageView.setOnClickListener {
            stopRecording()
        }
        submitRecordingImageView.setOnClickListener {
            submitRecording()
        }
        closeRecordingImageView.setOnClickListener {
            toNotRecordingState()
        }
    }

    private fun startRecording() {
        if (!requestPermissions()) {
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val nameOfRecording = sessionViewModel.getNameForNewRecording()
            if (nameOfRecording == null) {
                Toast.makeText(requireContext(), "Не удалось начать запись.", Toast.LENGTH_LONG)
                    .show()
                return@launch
            }
            lifecycleScope.launch(Dispatchers.Main) {
                timerTextView.text = "00:00"
                recordingNameTextView.text = nameOfRecording
                newRecordingTextView.visibility = View.GONE
                startRecordingImageView.visibility = View.GONE
                stopRecordingImageView.visibility = View.VISIBLE
                timerTextView.visibility = View.VISIBLE
                recordingNameTextView.visibility = View.VISIBLE
                recorderWorker.startRecording(nameOfRecording)
            }
        }
    }

    private fun stopRecording() {
        recorderWorker.stopRecording()
        stopRecordingImageView.visibility = View.GONE
        descriptionEditText.visibility = View.VISIBLE
        closeRecordingImageView.visibility = View.VISIBLE
        submitRecordingImageView.visibility = View.VISIBLE
    }

    private fun submitRecording() {
        sessionViewModel.addNewRecording(
            RecordingCreatedModel(
                path = sessionViewModel.getNameOfRecording(),
                description = descriptionEditText.text.toString(),
                duration = recorderWorker.recordingDuration
            )
        )
        toNotRecordingState()
    }

    private fun toNotRecordingState(){
        closeRecordingImageView.visibility = View.GONE
        descriptionEditText.visibility = View.GONE
        timerTextView.visibility = View.GONE
        newRecordingTextView.visibility = View.VISIBLE
        recordingNameTextView.visibility = View.GONE
        submitRecordingImageView.visibility = View.GONE
        startRecordingImageView.visibility = View.VISIBLE
    }

    private fun requestPermissions(): Boolean {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.RECORD_AUDIO),
            1
        )
        // permissionLauncher.launch(RECORD_AUDIO)
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECORD_AUDIO
        ) ==
                PackageManager.PERMISSION_GRANTED
    }

}