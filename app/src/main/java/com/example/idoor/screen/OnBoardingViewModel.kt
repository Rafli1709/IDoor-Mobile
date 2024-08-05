package com.example.idoor.screen

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    fun saveStartDestination() {
        viewModelScope.launch {
            repository.saveStartDestination(startDestination = Screen.Login.route)
        }
    }
}