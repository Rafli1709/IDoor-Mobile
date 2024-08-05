package com.example.idoor.screen.admin.manageuser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.users.CreateUserEntry
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")

    fun createUser(createUserEntry: CreateUserEntry) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.createUser(createUserEntry)) {
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