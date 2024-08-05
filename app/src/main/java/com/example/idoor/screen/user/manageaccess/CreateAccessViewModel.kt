package com.example.idoor.screen.user.manageaccess

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.components.DropdownOption
import com.example.idoor.data.models.accessright.CreateAccessRight
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.AccessRightRepository
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccessViewModel @Inject constructor(
    private val accessRightRepository: AccessRightRepository,
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id")

    var userList = mutableStateOf<List<DropdownOption>>(listOf())

    private var cachedUserList = listOf<DropdownOption>()
    private var isSearch = true

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")

    var isLoadingDropdown = mutableStateOf(false)
    var errorMessageDropdown = mutableStateOf("")

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

    fun createAccessRight(createAccessRight: CreateAccessRight) {
        viewModelScope.launch {
            isLoading.value = true
            Log.d("Test", createAccessRight.toString())
            when (val result = accessRightRepository.createAccessRight(createAccessRight)) {
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