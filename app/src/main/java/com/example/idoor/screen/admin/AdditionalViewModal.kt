package com.example.idoor.screen.admin

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdditionalViewModal @Inject constructor(
private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var userName by mutableStateOf("")

    init {
        viewModelScope.launch {
            dataStoreRepository.readUserName()
                .collect { name ->
                    userName = name ?: ""
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreRepository.clearAuthData()
            dataStoreRepository.saveStartDestination(Screen.Login.route)
        }
    }
}