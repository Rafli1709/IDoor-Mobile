package com.example.idoor.screen.user.manageaccess

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.accessright.DetailAccessRightEntry
import com.example.idoor.repository.AccessRightRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAccessViewModel @Inject constructor(
    private val repository: AccessRightRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id")

    var accessRightList = mutableStateOf<List<DetailAccessRightEntry>>(listOf())

    private var cachedAccessRightList = listOf<DetailAccessRightEntry>()
    private var isSearch = true

    var isDialogShown = mutableStateOf(false)
        private set

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    var isLoadingAction = mutableStateOf(false)
    var errorMessageAction = mutableStateOf("")
    var successMessageAction = mutableStateOf("")

    init {
        getAccessRightList()
    }

    fun searchAccessRightList(query: String) {
        val listToSearch = if (isSearch) {
            accessRightList.value
        } else {
            cachedAccessRightList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                accessRightList.value = cachedAccessRightList
                isSearch = true
                return@launch
            }
            val results = listToSearch.filter {
                it.email.contains(query.trim(), ignoreCase = true) ||
                        it.userName.contains(query.trim(), ignoreCase = true) ||
                        it.timeLimit?.contains(query.trim(), ignoreCase = true) ?: false
            }
            if (isSearch) {
                cachedAccessRightList = accessRightList.value
                isSearch = false
            }
            accessRightList.value = results
        }
    }

    fun getAccessRightList() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getAccessRightInfo(id!!)) {
                is Resource.Success -> {
                    val accessRightEntries = result.data?.map { entry ->
                        DetailAccessRightEntry(
                            id = entry.id,
                            userName = entry.user.name,
                            email = entry.user.email,
                            timeLimit = entry.batas_waktu
                        )
                    }

                    errorMessage.value = ""
                    isLoading.value = false
                    accessRightList.value = accessRightEntries!!
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun deleteAccessRight(id: Int) {
        viewModelScope.launch {
            isLoadingAction.value = true
            when (val result = repository.deleteAccessRight(id)) {
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

    fun onDetailClick() {
        isDialogShown.value = true
    }

    fun onDismissDialog() {
        isDialogShown.value = false
    }
}