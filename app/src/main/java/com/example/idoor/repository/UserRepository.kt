package com.example.idoor.repository

import com.example.idoor.data.models.users.CreateUserEntry
import com.example.idoor.data.models.users.UpdateUserEntry
import com.example.idoor.data.remote.AuthApiInterface
import com.example.idoor.data.remote.responses.CudResponse
import com.example.idoor.data.remote.responses.User
import com.example.idoor.data.remote.responses.UsersResponse
import com.example.idoor.util.Resource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: AuthApiInterface
){
    suspend fun getUsers(): Resource<UsersResponse> {
        val response = try {
            api.getUsers()
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun createUser(createUserEntry: CreateUserEntry): Resource<CudResponse> {
        val response = try {
            api.createUser(createUserEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun getUserInfo(id: Int): Resource<User> {
        val response = try {
            api.getUserInfo(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun updateUser(id: Int, updateUserEntry: UpdateUserEntry): Resource<CudResponse> {
        val response = try {
            api.updateUser(id, updateUserEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun deleteUser(id: Int): Resource<CudResponse> {
        val response = try {
            api.deleteUser(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }
}