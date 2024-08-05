package com.example.idoor.screen.user.manageusertool

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.tools.CreateToolEntry
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.ToolRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserToolViewModel @Inject constructor(
    private val toolRepository: ToolRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var userId = mutableIntStateOf(0)
    private var deviceId = mutableStateOf("")

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")

    init {
        getDeviceId()
        getUserId()
    }

    private fun getDeviceId() {
        viewModelScope.launch {
            dataStoreRepository.readDeviceId()
                .collect { id ->
                    deviceId.value = id
                }
        }
    }

    private fun getUserId() {
        viewModelScope.launch {
            dataStoreRepository.readUserId()
                .collect { id ->
                    userId.intValue = id ?: 0
                }
        }
    }

    fun createTool(userId: Int, name: String, plaintext: String) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = toolRepository.createTool(
                CreateToolEntry(
                    userId = userId,
                    name = name,
                    plaintext = plaintext,
                    imei = deviceId.value
                )
            )) {
                is Resource.Success -> {
                    successMessage.value = result.data!!.message
                    errorMessage.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    successMessage.value = ""
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}