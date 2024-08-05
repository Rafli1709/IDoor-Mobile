package com.example.idoor.screen.admin

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.repository.ToolRepository
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAdminViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val toolRepository: ToolRepository
) : ViewModel() {
    var userCount = mutableIntStateOf(0)
    var toolCount = mutableIntStateOf(0)

    var isLoadingUser = mutableStateOf(false)
    var errorMessageUser = mutableStateOf("")

    var isLoadingTool = mutableStateOf(false)
    var errorMessageTool = mutableStateOf("")

    init {
        getTotalUser()
        getTotalTool()
    }

    fun getTotalUser() {
        viewModelScope.launch {
            isLoadingUser.value = true
            when (val result = userRepository.getUsers()) {
                is Resource.Success -> {
                    errorMessageUser.value = ""
                    isLoadingUser.value = false
                    userCount.intValue = result.data!!.totalUser
                }

                is Resource.Error -> {
                    errorMessageUser.value = result.message!!
                    isLoadingUser.value = false
                }
            }
        }
    }

    fun getTotalTool() {
        viewModelScope.launch {
            isLoadingTool.value = true
            when (val result = toolRepository.getTools()) {
                is Resource.Success -> {
                    errorMessageTool.value = ""
                    isLoadingTool.value = false
                    toolCount.intValue = result.data!!.totalAlat
                }

                is Resource.Error -> {
                    errorMessageTool.value = result.message!!
                    isLoadingTool.value = false
                }
            }
        }
    }
}