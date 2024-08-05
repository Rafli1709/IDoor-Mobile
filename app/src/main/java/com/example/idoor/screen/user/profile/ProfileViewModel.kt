package com.example.idoor.screen.user.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.auth.AuthData
import com.example.idoor.data.models.auth.UserData
import com.example.idoor.data.models.users.UpdateProfileEntry
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.navigation.Screen
import com.example.idoor.repository.ProfileRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var authData = mutableStateOf<AuthData?>(null)

    var isLoading = mutableStateOf(false)
    var isSuccess = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    init {
        getAuthData()
    }

    fun getAuthData() {
        viewModelScope.launch {
            dataStoreRepository.readAuthData()
                .collect { data ->
                    authData.value = data
                }
        }
    }

    fun updateUser(updateProfileEntry: UpdateProfileEntry) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = profileRepository.updateProfile(authData.value?.user!!.id, updateProfileEntry)) {
                is Resource.Success -> {
                    val userData = UserData(
                        id = result.data!!.id,
                        name = result.data.name,
                        username = result.data.username,
                        email = result.data.email,
                        role = result.data.role,
                        phoneNumber = result.data.profile.no_hp,
                        gender = result.data.profile.jenis_kelamin,
                        dateBirth = result.data.profile.tgl_lahir,
                        address = result.data.profile.alamat,
                    )

                    dataStoreRepository.updateUserData(userData)

                    errorMessage.value = ""
                    isSuccess.value = true
                    isLoading.value = false
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isSuccess.value = false
                    isLoading.value = false
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreRepository.saveStartDestination(Screen.Login.route)
        }
    }
}