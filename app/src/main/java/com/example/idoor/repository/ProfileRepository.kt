package com.example.idoor.repository

import com.example.idoor.data.models.users.CreateUserEntry
import com.example.idoor.data.models.users.UpdateProfileEntry
import com.example.idoor.data.models.users.UpdateUserEntry
import com.example.idoor.data.remote.AuthApiInterface
import com.example.idoor.data.remote.responses.CudResponse
import com.example.idoor.data.remote.responses.ProfileResponse
import com.example.idoor.data.remote.responses.User
import com.example.idoor.data.remote.responses.UsersResponse
import com.example.idoor.util.Resource
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: AuthApiInterface
){
    suspend fun updateProfile(id: Int, updateProfileEntry: UpdateProfileEntry): Resource<ProfileResponse> {
        val response = try {
            api.updateProfile(id, updateProfileEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }
}