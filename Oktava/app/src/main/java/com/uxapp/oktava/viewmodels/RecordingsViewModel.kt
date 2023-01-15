package com.uxapp.oktava.viewmodels

import androidx.lifecycle.ViewModel
import com.uxapp.oktava.storage.repositories.RecordingsRepository

class RecordingsViewModel(
    private val recordingsRep: RecordingsRepository
): ViewModel() {
}