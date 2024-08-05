package com.example.idoor.screen.admin.manageuser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.users.UserEntry
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    var userList = mutableStateOf<List<UserEntry>>(listOf())
    var userInfo = mutableStateOf<UserEntry?>(null)

    private var cachedUserList = listOf<UserEntry>()
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
        getUserList()
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
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.email.contains(query.trim(), ignoreCase = true)
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
            isLoading.value = true
            when (val result = repository.getUsers()) {
                is Resource.Success -> {
                    val userEntries = result.data?.users!!.map { entry ->
                        UserEntry(entry.id, entry.name, entry.username, entry.email)
                    }

                    errorMessage.value = ""
                    isLoading.value = false
                    userList.value = userEntries
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun getUserInfo(id: Int) {
        viewModelScope.launch {
            isLoadingModal.value = true
            when (val result = repository.getUserInfo(id)) {
                is Resource.Success -> {
                    errorMessageModal.value = ""
                    isLoadingModal.value = false
                    userInfo.value = UserEntry(
                        id = result.data!!.id,
                        name = result.data.name,
                        username = result.data.username,
                        email = result.data.email,
                    )
                }

                is Resource.Error -> {
                    errorMessageModal.value = result.message!!
                    isLoadingModal.value = false
                }
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            isLoadingAction.value = true
            when (val result = repository.deleteUser(id)) {
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