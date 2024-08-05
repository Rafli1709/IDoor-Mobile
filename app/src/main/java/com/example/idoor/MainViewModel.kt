package com.example.idoor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    var isLoading by mutableStateOf(true)
    var startDestination by mutableStateOf("")

    init {
        viewModelScope.launch {
            repository.readStartDestination().collect { route ->
                startDestination = route
                isLoading = false
            }
        }
    }

    fun saveDeviceInfo(deviceId: String, deviceModel: String) {
        viewModelScope.launch {
            repository.saveDeviceId(id = deviceId)
            repository.saveDeviceModel(deviceModel = deviceModel)
        }
    }
}