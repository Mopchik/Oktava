package com.uxapp.oktava.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uxapp.oktava.R
import com.uxapp.oktava.ui.adapters.notes.SongsAdapter
import com.uxapp.oktava.ui.main.MainActivity
import kotlinx.coroutines.flow.Flow

abstract class BaseSongsFragment<SongModel> : Fragment() {

    private lateinit var notesCompletedRecyclerView: RecyclerView
    private lateinit var notesInProgressRecyclerView: RecyclerView
    private lateinit var notesNotYetRecyclerView: RecyclerView

    private lateinit var notesInProgressTextView: TextView
    private lateinit var notesNotYetTextView: TextView
    private lateinit var notesCompletedTextView: TextView

    private lateinit var notesCompletedAdapter: ListAdapter<SongModel, out RecyclerView.ViewHolder>
    private lateinit var notesInProgressAdapter: ListAdapter<SongModel, out RecyclerView.ViewHolder>
    private lateinit var notesNotYetAdapter: ListAdapter<SongModel, out RecyclerView.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_songs, container, false)
        setupViews(view)
        setupRecyclers()
        return view
    }

    abstract fun getAdapter(): ListAdapter<SongModel, out RecyclerView.ViewHolder>

    private fun setupViews(view: View) {
        notesCompletedRecyclerView = view.findViewById(R.id.notesCompletedRecyclerView)
        notesInProgressRecyclerView = view.findViewById(R.id.notesInProgressRecyclerView)
        notesNotYetRecyclerView = view.findViewById(R.id.notesNotYetRecyclerView)

        notesInProgressTextView = view.findViewById(R.id.textViewNotesInProgress)
        notesNotYetTextView = view.findViewById(R.id.textViewNotesNotYet)
        notesCompletedTextView = view.findViewById(R.id.textViewNotesCompleted)
    }

    private fun setupRecyclers() {
        notesCompletedAdapter = getAdapter()
        notesInProgressAdapter = getAdapter()
        notesNotYetAdapter = getAdapter()

        notesCompletedRecyclerView.adapter = notesCompletedAdapter
        notesInProgressRecyclerView.adapter = notesInProgressAdapter
        notesNotYetRecyclerView.adapter = notesNotYetAdapter
        notesCompletedRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        notesInProgressRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        notesNotYetRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        observeData()
    }

    protected abstract fun getCompletedSongsFlow(): Flow<List<SongModel>>
    protected abstract fun getInProgressSongsFlow(): Flow<List<SongModel>>
    protected abstract fun getNotStartedSongsFlow(): Flow<List<SongModel>>

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getCompletedSongsFlow().collect {
                notesCompletedTextView.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                notesCompletedAdapter.submitList(it)
                notesCompletedAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getInProgressSongsFlow().collect {
                notesInProgressTextView.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                notesInProgressAdapter.submitList(it)
                notesInProgressAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getNotStartedSongsFlow().collect {
                notesNotYetTextView.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                notesNotYetAdapter.submitList(it)
                notesInProgressAdapter.notifyDataSetChanged()
            }
        }
    }
}
