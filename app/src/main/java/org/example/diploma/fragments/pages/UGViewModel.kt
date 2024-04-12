package org.example.diploma.fragments.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.diploma.database.amplifier.AmplifierEntity
import org.example.diploma.database.amplifier.AmplifierRepository

class UGViewModel(private val repository: AmplifierRepository) : ViewModel() {
    val initialAmplifier : LiveData<AmplifierEntity> = repository.initialAmplifier
}
class UGViewModelFactory(private val repository: AmplifierRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UGViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UGViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}