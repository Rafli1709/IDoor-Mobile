package com.example.idoor.screen.admin.managetools

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.components.DropdownOption
import com.example.idoor.data.models.tools.CreateToolEntry
import com.example.idoor.data.models.tools.ToolEntry
import com.example.idoor.repository.ToolRepository
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateToolViewModel @Inject constructor(
    private val toolRepository: ToolRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>("id")
    var toolInfo = mutableStateOf<ToolEntry?>(null)

    var userList = mutableStateOf<List<DropdownOption>>(listOf())

    private var cachedUserList = listOf<DropdownOption>()
    private var isSearch = true

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    var isLoadingModal = mutableStateOf(false)
    var errorMessageModal = mutableStateOf("")
    var successMessageModal = mutableStateOf("")

    var isLoadingDropdown = mutableStateOf(false)
    var errorMessageDropdown = mutableStateOf("")

    init {
        getUserList()
        getToolInfo(id!!)
    }

    fun searchUserList(query: String) {
        val listToSearch = if (isSearch) {
            userList.value
        } else {
            cachedUserList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                userList.value = cachedUserList
                isSearch = true
                return@launch
            }
            val results = listToSearch.filter {
                it.text.contains(query.trim(), ignoreCase = true)
            }
            if (isSearch) {
                cachedUserList = userList.value
                isSearch = false
            }
            userList.value = results
        }
    }

    fun getUserList() {
        viewModelScope.launch {
            isLoadingDropdown.value = true
            when (val result = userRepository.getUsers()) {
                is Resource.Success -> {
                    val userEntries = result.data?.users!!.map { entry ->
                        DropdownOption(
                            value = entry.id,
                            text = entry.email
                        )
                    }

                    errorMessageDropdown.value = ""
                    isLoadingDropdown.value = false
                    userList.value += userEntries
                }

                is Resource.Error -> {
                    errorMessageDropdown.value = result.message!!
                    isLoadingDropdown.value = false
                }
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

    fun updateTool(id: Int, createToolEntry: CreateToolEntry) {
        viewModelScope.launch {
            isLoadingModal.value = true
            when (val result = toolRepository.updateTool(id, createToolEntry)) {
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