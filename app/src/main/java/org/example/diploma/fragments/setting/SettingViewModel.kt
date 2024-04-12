package org.example.diploma.fragments.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.diploma.database.host.HostEntity
import org.example.diploma.database.host.HostRepository

class SettingViewModel (private val repository: HostRepository) : ViewModel() {
    var host : LiveData<HostEntity> = repository.getHostData(1L)

    val hostId : MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

}
class SettingViewModelFactory(private val repository: HostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}