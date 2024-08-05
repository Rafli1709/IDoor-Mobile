package com.example.idoor.screen.admin.manageuser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.users.UpdateUserEntry
import com.example.idoor.data.models.users.UserEntry
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    private val repository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val id = savedStateHandle.get<Int>("id")
    var userInfo = mutableStateOf<UserEntry?>(null)

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    var isLoadingModal = mutableStateOf(false)
    var errorMessageModal = mutableStateOf("")
    var successMessageModal = mutableStateOf("")

    init {
        getUserInfo(id!!)
    }

    fun getUserInfo(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getUserInfo(id)) {
                is Resource.Success -> {
                    errorMessage.value = ""
                    isLoading.value = false
                    userInfo.value = UserEntry(
                        id = result.data!!.id,
                        name = result.data.name,
                        username = result.data.username,
                        email = result.data.email,
                    )
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun updateUser(id: Int, updateUserEntry: UpdateUserEntry) {
        viewModelScope.launch {
            isLoadingModal.value = true
            when (val result = repository.updateUser(id, updateUserEntry)) {
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