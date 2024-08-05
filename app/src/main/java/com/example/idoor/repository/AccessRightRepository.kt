package com.example.idoor.repository

import android.util.Log
import com.example.idoor.data.models.accessright.CreateAccessRight
import com.example.idoor.data.remote.AuthApiInterface
import com.example.idoor.data.remote.responses.AccessRightResponse
import com.example.idoor.data.remote.responses.CudResponse
import com.example.idoor.data.remote.responses.DetailAccessRightResponse
import com.example.idoor.data.remote.responses.HakAkses
import com.example.idoor.util.Resource
import javax.inject.Inject

class AccessRightRepository @Inject constructor(
    private val api: AuthApiInterface
){
    suspend fun getAccessRights(id: Int): Resource<AccessRightResponse> {
        val response = try {
            api.getAccessRights(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun createAccessRight(createAccessRight: CreateAccessRight): Resource<CudResponse> {
        val response = try {
            api.createAccessRight(createAccessRight)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun getAccessRightInfo(id: Int): Resource<DetailAccessRightResponse> {
        val response = try {
            api.getAccessRightInfo(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun deleteAccessRight(id: Int): Resource<CudResponse> {
        val response = try {
            api.deleteAccessRight(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }
}