package com.example.idoor.repository

import com.example.idoor.data.models.auth.LoginEntry
import com.example.idoor.data.models.auth.RegisterEntry
import com.example.idoor.data.remote.GuestApiInterface
import com.example.idoor.data.remote.responses.AuthResponse
import com.example.idoor.util.Resource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: GuestApiInterface
){
    suspend fun login(loginEntry: LoginEntry): Resource<AuthResponse> {
        val response = try {
            api.login(loginEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun register(registerEntry: RegisterEntry): Resource<AuthResponse> {
        val response = try {
            api.register(registerEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }
}