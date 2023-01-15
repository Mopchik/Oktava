package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import com.uxapp.oktava.storage.repositories.SongsRepository

class SongsViewModel(
    private val songsRep: SongsRepository
): ViewModel() {

}