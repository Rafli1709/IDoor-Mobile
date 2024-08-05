package com.example.idoor.screen.user.manageusertool

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.accessright.AccessRightEntry
import com.example.idoor.data.models.tools.ToolEntry
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.AccessRightRepository
import com.example.idoor.repository.ToolRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageUserToolViewModel @Inject constructor(
    private val accessRightRepository: AccessRightRepository,
    private val toolRepository: ToolRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var userId = mutableIntStateOf(0)

    var toolList = mutableStateOf<List<AccessRightEntry>>(listOf())
    var toolInfo = mutableStateOf<ToolEntry?>(null)

    private var cachedToolList = listOf<AccessRightEntry>()
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
        getUserId()
        getToolList()
    }

    private fun getUserId() {
        viewModelScope.launch {
            dataStoreRepository.readUserId()
                .collect { id ->
                    userId.intValue = id ?: 0
                }
        }
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
                        it.status.contains(query.trim(), ignoreCase = true) ||
                        it.timeLimit?.contains(query.trim(), ignoreCase = true) ?: false
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
            when (val result = accessRightRepository.getAccessRights(userId.intValue)) {
                is Resource.Success -> {
                    val toolEntries = result.data?.alat!!.map { entry ->
                        AccessRightEntry(
                            id = entry.id,
                            toolName = entry.nama,
                            status = if (entry.hak_akses == null || entry.user_id == userId.intValue) "Master" else "Pinjam",
                            timeLimit = if (entry.hak_akses == null || entry.user_id == userId.intValue) null else entry.hak_akses.batas_waktu
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
            when (val result = toolRepository.getToolInfo(id)) {
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
            when (val result = toolRepository.deleteTool(id)) {
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