package com.uxapp.oktava.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.utils.setupImageViewFromFile
import com.uxapp.oktava.utils.setupTextViewNotEmpty
import com.uxapp.oktava.viewmodels.dataModels.SongCardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongCardFragment : Fragment() {

    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    private lateinit var nameTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var albumTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var authorTitleTextView: TextView
    private lateinit var albumTitleTextView: TextView
    private lateinit var yearTitleTextView: TextView
    private lateinit var genreTitleTextView: TextView

    private lateinit var pictureImageView: ImageView
    private lateinit var notesImageView: ImageView
    private lateinit var wordsTextView: TextView
    private lateinit var wordsNotesTabLayout: TabLayout

    private lateinit var deleteImageButton: ImageButton
    private lateinit var editImageButton: ImageButton
    private lateinit var backImageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        nameTextView = view.findViewById(R.id.nameSongCardTextView)
        authorTextView = view.findViewById(R.id.authorSongCardTextView)
        albumTextView = view.findViewById(R.id.albumSongCardTextView)
        genreTextView = view.findViewById(R.id.genreSongCardTextView)
        yearTextView = view.findViewById(R.id.yearSongCardTextView)

        authorTitleTextView = view.findViewById(R.id.authorTitleTextView)
        albumTitleTextView = view.findViewById(R.id.albumTitleTextView)
        genreTitleTextView = view.findViewById(R.id.genreTitleTextView)
        yearTitleTextView = view.findViewById(R.id.yearTitleTextView)

        deleteImageButton = view.findViewById(R.id.deleteCardImageButton)
        editImageButton = view.findViewById(R.id.editCardImageButton)
        backImageButton = view.findViewById(R.id.backSongCardButton)

        pictureImageView = view.findViewById(R.id.songCardImageView)
        notesImageView = view.findViewById(R.id.notesSongCardImageView)
        wordsTextView = view.findViewById(R.id.wordsSongCardTextView)
        wordsNotesTabLayout = view.findViewById(R.id.wordsNotesTabLayout)

        setupSongCardInfo()
        setupButtons()
    }

    private fun setupSongCardInfo() {
        if (!songsViewModel.songsChooseProcess.isChosen()) {
            return
        }
        lifecycleScope.launch(Dispatchers.Main) {
            val chosenSongId = songsViewModel.songsChooseProcess.getChosenSongId()
            val songCardModel = songsViewModel.getCardModelOfSong(chosenSongId) ?: return@launch
            nameTextView.setupTextViewNotEmpty(songCardModel.name)
            authorTextView.setupTextViewNotEmpty(songCardModel.author, authorTitleTextView)
            albumTextView.setupTextViewNotEmpty(songCardModel.album, albumTitleTextView)
            yearTextView.setupTextViewNotEmpty(songCardModel.year, yearTitleTextView)
            genreTextView.setupTextViewNotEmpty(songCardModel.genresAsString, genreTitleTextView)
            pictureImageView.setupImageViewFromFile(songCardModel.filePicture)
            setupNotesAndWords(songCardModel)
        }
    }

    private fun setupButtons() {
        deleteImageButton.setOnClickListener {
            songsViewModel.deleteSongById(songsViewModel.songsChooseProcess.getChosenSongId())
            requireActivity().onBackPressed()
        }
        editImageButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, CreateAndEditNotesFragment())
                .addToBackStack(null)
                .commit()
        }
        backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupNotesAndWords(songCardModel: SongCardModel) {
        notesImageView.setupImageViewFromFile(songCardModel.fileNotesForPiano)
        if(songCardModel.words != null) {
            wordsTextView.text = songCardModel.words
        }
        wordsNotesTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return
                when (tab.position) {
                    0 -> {
                        notesImageView.visibility = View.INVISIBLE
                        wordsTextView.visibility = View.VISIBLE
                    }
                    1 -> {
                        wordsTextView.visibility = View.GONE
                        notesImageView.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}