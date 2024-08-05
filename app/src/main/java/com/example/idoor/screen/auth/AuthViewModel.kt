package com.example.idoor.screen.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.auth.AuthData
import com.example.idoor.data.models.auth.LoginEntry
import com.example.idoor.data.models.auth.RegisterEntry
import com.example.idoor.data.models.auth.UserData
import com.example.idoor.data.remote.TokenInterceptor
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.navigation.Screen
import com.example.idoor.repository.AuthRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStore: DataStoreRepository,
    private val tokenInterceptor: TokenInterceptor
) : ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun login(loginEntry: LoginEntry) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.login(loginEntry)) {
                is Resource.Success -> {
                    val userData = UserData(
                        id = result.data!!.user.id,
                        name = result.data.user.name,
                        username = result.data.user.username,
                        email = result.data.user.email,
                        role = result.data.user.role,
                        phoneNumber = result.data.user.profile.no_hp,
                        gender = result.data.user.profile.jenis_kelamin,
                        dateBirth = result.data.user.profile.tgl_lahir,
                        address = result.data.user.profile.alamat,
                    )

                    dataStore.saveAuthData(AuthData(user = userData, token = result.data.token))
                    tokenInterceptor.setToken(result.data.token)

                    val startDestination = if (userData.role == "user") {
                        Screen.DashboardUser.route
                    } else {
                        Screen.DashboardAdmin.route
                    }

                    dataStore.saveStartDestination(startDestination)
                    delay(700)

                    errorMessage.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun register(registerEntry: RegisterEntry) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.register(registerEntry)) {
                is Resource.Success -> {
                    val userData = UserData(
                        id = result.data!!.user.id,
                        name = result.data.user.name,
                        username = result.data.user.username,
                        email = result.data.user.email,
                        role = result.data.user.role,
                        phoneNumber = "",
                        gender = "",
                        dateBirth = "",
                        address = "",
                    )

                    dataStore.saveAuthData(AuthData(user = userData, token = result.data.token))
                    tokenInterceptor.setToken(result.data.token)

                    dataStore.saveStartDestination(Screen.DashboardUser.route)
                    delay(700)

                    errorMessage.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}