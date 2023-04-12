package com.uxapp.oktava.ui.main.notes

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.uxapp.oktava.R
import com.uxapp.oktava.file_worker.PathUtil
import com.uxapp.oktava.file_worker.image.ImageReader
import com.uxapp.oktava.ui.main.MainActivity
import com.uxapp.oktava.ui.main.models.SongAboutModel
import com.uxapp.oktava.utils.Genre
import com.uxapp.oktava.utils.StringConverter
import com.uxapp.oktava.utils.setTextIfNotEquals


class AboutNotesFragment : Fragment() {

    private val songsViewModel by lazy {
        (requireActivity() as MainActivity).songsViewModel
    }

    private lateinit var nameEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var albumEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var genresChipGroup: ChipGroup
    private lateinit var imageFrameLayout: FrameLayout
    private lateinit var buttonChooseImage: Button
    private lateinit var chosenImageView: ImageView
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
                songsViewModel.songsCreationProcess.setFilePicture(
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
        val view = inflater.inflate(R.layout.fragment_about_song, container, false)
        setUpViews(view)
        return view
    }

    private fun setUpViews(view: View) {
        nameEditText = view.findViewById(R.id.nameEditText)
        authorEditText = view.findViewById(R.id.authorEditText)
        albumEditText = view.findViewById(R.id.albomEditText)
        yearEditText = view.findViewById(R.id.yearEditText)
        genresChipGroup = view.findViewById(R.id.chooseGenreChipGroup)
        imageFrameLayout = view.findViewById(R.id.imageFrameLayout)
        buttonChooseImage = view.findViewById(R.id.buttonAddImage)
        chosenImageView = view.findViewById(R.id.chosenImageView)

        setUpDataFields()
    }

    private fun setUpDataFields() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            songsViewModel.songsCreationProcess.getSongAboutFlow().collect { songAboutModel ->
                nameEditText.setTextIfNotEquals(songAboutModel.name)
                authorEditText.setTextIfNotEquals(songAboutModel.author)
                albumEditText.setTextIfNotEquals(songAboutModel.album)
                yearEditText.setTextIfNotEquals(songAboutModel.year)
                setupImageView(songAboutModel)
                setUpGenresChipGroup(songAboutModel)
            }
        }

        nameEditText.addTextChangedListener {
            songsViewModel.songsCreationProcess.setName(it.toString())
        }
        authorEditText.addTextChangedListener {
            songsViewModel.songsCreationProcess.setAuthor(it.toString())
        }
        albumEditText.addTextChangedListener {
            songsViewModel.songsCreationProcess.setAlbum(it.toString())
        }
        yearEditText.addTextChangedListener {
            songsViewModel.songsCreationProcess.setYear(it.toString())
        }
    }

    private fun setupImageView(songAboutModel: SongAboutModel) {
        val imageBitmap = if ((requireActivity() as MainActivity).requestPermissions()) {
            ImageReader.readSafeBitmapFromFileAbsolutePathWithWidth(
                songAboutModel.filePicture, imageFrameLayout.layoutParams.width
            )
        } else null
        if (imageBitmap == null) {
            buttonChooseImage.visibility = View.VISIBLE
            chosenImageView.visibility = View.GONE
            buttonChooseImage.setOnClickListener {
                startChoosingImage()
            }
        } else {
            chosenImageView.setImageBitmap(imageBitmap)
            chosenImageView.setOnClickListener {
                startChoosingImage()
            }
            buttonChooseImage.visibility = View.GONE
            chosenImageView.visibility = View.VISIBLE
        }

    }

    private fun setUpGenresChipGroup(songAboutModel: SongAboutModel) {
        genresChipGroup.children.forEach {
            val chip = (it as Chip)
            chip.isChecked = songAboutModel.genres.contains(
                StringConverter.genreFromString(chip.text.toString())
            )
        }
        genresChipGroup.setOnCheckedStateChangeListener { _, _ ->
            songsViewModel.songsCreationProcess.setGenres(getListOfGenres())
        }
    }

    private fun getListOfGenres(): List<Genre> {
        val genres = ArrayList<Genre>()
        val ids: List<Int> = genresChipGroup.checkedChipIds
        for (id in ids) {
            val chip: Chip = genresChipGroup.findViewById(id)
            genres.add(StringConverter.genreFromString(chip.text.toString()))
        }
        return genres
    }

    private fun startChoosingImage() {
        activityResultLauncher.launch("image/*")
    }

    private fun onImageChose(path: Uri) {
        chosenImageView.setImageURI(path)
        chosenImageView.visibility = View.VISIBLE
        buttonChooseImage.visibility = View.GONE
    }
}