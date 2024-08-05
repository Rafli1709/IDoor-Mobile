package com.example.idoor.screen.admin.managetools

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.tools.ToolEntry
import com.example.idoor.repository.ToolRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageToolViewModel @Inject constructor(
    private val repository: ToolRepository
) : ViewModel() {
    var toolList = mutableStateOf<List<ToolEntry>>(listOf())
    var toolInfo = mutableStateOf<ToolEntry?>(null)

    private var cachedToolList = listOf<ToolEntry>()
    private var isSearch = true

    var isDialogShown = mutableStateOf(false)
        private set

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    var isLoadingModal = mutableStateOf(false)
    var errorMessageModal = mutableStateOf("")

    var isLoadingAction = mutableStateOf(false)
    var errorMessageAction = mutableStateOf("")
    var successMessageAction = mutableStateOf("")

    init {
        getToolList()
    }

    fun searchToolList(query: String) {
        val listToSearch = if (isSearch) {
            toolList.value
        } else {
            cachedToolList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                toolList.value = cachedToolList
                isSearch = true
                return@launch
            }
            val results = listToSearch.filter {
                it.toolName.contains(query.trim(), ignoreCase = true) ||
                        it.emailUser.contains(query.trim(), ignoreCase = true)
            }
            if (isSearch) {
                cachedToolList = toolList.value
                isSearch = false
            }
            toolList.value = results
        }
    }

    fun getToolList() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getTools()) {
                is Resource.Success -> {
                    val toolEntries = result.data?.alat!!.map { entry ->
                        ToolEntry(
                            id = entry.id,
                            userId = entry.user_id,
                            toolName = entry.nama,
                            emailUser = entry.user.email,
                            imei = entry.imei,
                            password = entry.password,
                        )
                    }

                    errorMessage.value = ""
                    isLoading.value = false
                    toolList.value = toolEntries
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun getToolInfo(id: Int) {
        viewModelScope.launch {
            isLoadingModal.value = true
            when (val result = repository.getToolInfo(id)) {
                is Resource.Success -> {
                    errorMessageModal.value = ""
                    isLoadingModal.value = false
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
                    errorMessageModal.value = result.message!!
                    isLoadingModal.value = false
                }
            }
        }
    }

    fun deleteTool(id: Int) {
        viewModelScope.launch {
            isLoadingAction.value = true
            when (val result = repository.deleteTool(id)) {
                is Resource.Success -> {
                    successMessageAction.value = result.data!!.message
                    errorMessageAction.value = ""
                    isLoadingAction.value = false
                }

                is Resource.Error -> {
                    errorMessageAction.value = result.message!!
                    successMessageAction.value = ""
                    isLoadingAction.value = false
                }
            }
        }
    }

    fun onDetailClick(){
        isDialogShown.value = true
    }

    fun onDismissDialog(){
        isDialogShown.value = false
    }
}