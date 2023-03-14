package com.uxapp.oktava.ui.main.notes

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.PathUtil
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.ui.main.MainActivity


class NotesOfNotesFragment : Fragment() {

    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    private lateinit var notesScrollView: ScrollView
    private lateinit var buttonChooseNotes: Button
    private lateinit var chosenNotesImageView: ImageView

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>
    private val uriToPathMapper by lazy {
        PathUtil(
            requireContext().applicationContext,
            requireActivity().contentResolver,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                songsViewModel.songsCreationProcess.setFileNotes(
                    uriToPathMapper.getFilePathFromUri(uri)
                )
                onImageChose(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_create_notes, container, false)
        notesScrollView = view.findViewById(R.id.notesCreateNotesScrollView)
        buttonChooseNotes = view.findViewById(R.id.buttonAddNotesCreateNotes)
        chosenNotesImageView = view.findViewById(R.id.chosenNotesCreateNotesImageView)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            songsViewModel.songsCreationProcess.getSongFileNotesFlow().collect {
                setupImageView(it)
            }
        }
        return view
    }

    private fun setupImageView(notesFilePath: String?) {
        val imageBitmap = if ((requireActivity() as MainActivity).requestPermissions()) {
            ImageReader.readSafeBitmapFromFileAbsolutePath(
                notesFilePath
            )
        } else null
        if (imageBitmap == null) {
            buttonChooseNotes.visibility = View.VISIBLE
            chosenNotesImageView.visibility = View.GONE
            buttonChooseNotes.setOnClickListener {
                startChoosingImage()
            }
        } else {
            chosenNotesImageView.setImageBitmap(imageBitmap)
            chosenNotesImageView.setOnClickListener {
                startChoosingImage()
            }
            buttonChooseNotes.visibility = View.GONE
            chosenNotesImageView.visibility = View.VISIBLE
        }
    }

    private fun startChoosingImage() {
        activityResultLauncher.launch("image/*")
    }

    private fun onImageChose(path: Uri) {
        chosenNotesImageView.setImageURI(path)
        chosenNotesImageView.visibility = View.VISIBLE
        buttonChooseNotes.visibility = View.GONE
    }
}