package com.example.idoor.screen.user.manageusertool

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.tools.CreateToolEntry
import com.example.idoor.data.models.tools.ToolEntry
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.ToolRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserToolViewModel @Inject constructor(
    private val toolRepository: ToolRepository,
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>("id")
    var userId = mutableIntStateOf(0)
    var toolInfo = mutableStateOf<ToolEntry?>(null)

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    var isLoadingModal = mutableStateOf(false)
    var errorMessageModal = mutableStateOf("")
    var successMessageModal = mutableStateOf("")

    init {
        getUserId()
        getToolInfo(id!!)
    }

    private fun getUserId() {
        viewModelScope.launch {
            dataStoreRepository.readUserId()
                .collect { id ->
                    userId.intValue = id ?: 0
                }
        }
    }

    fun getToolInfo(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = toolRepository.getToolInfo(id)) {
                is Resource.Success -> {
                    errorMessage.value = ""
                    isLoading.value = false
                    toolInfo.value = ToolEntry(
                        id = result.data!!.id,
                        userId = result.data.user_id,
                        toolName = result.data.nama,
                        emailUser = result.data.user.email,
                        imei = result.data.imei,
                        password = result.data.password,
                    )
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun updateTool(createToolEntry: CreateToolEntry) {
        viewModelScope.launch {
            isLoadingModal.value = true
            when (val result = toolRepository.updateTool(userId.intValue, createToolEntry)) {
                is Resource.Success -> {
                    successMessageModal.value = result.data!!.message
                    errorMessageModal.value = ""
                    isLoadingModal.value = false
                }

                is Resource.Error -> {
                    successMessageModal.value = ""
                    errorMessageModal.value = result.message!!
                    isLoadingModal.value = false
                }
            }
        }
    }
}