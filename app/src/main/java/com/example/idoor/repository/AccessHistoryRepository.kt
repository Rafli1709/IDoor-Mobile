package com.example.idoor.repository

import com.example.idoor.data.models.accesshistory.CreateAccessHistory
import com.example.idoor.data.remote.AuthApiInterface
import com.example.idoor.data.remote.responses.AccessHistoryResponse
import com.example.idoor.data.remote.responses.CudResponse
import com.example.idoor.util.Resource
import javax.inject.Inject

class AccessHistoryRepository @Inject constructor(
    private val api: AuthApiInterface
){
    suspend fun getAccessHistories(id: Int): Resource<AccessHistoryResponse> {
        val response = try {
            api.getAccessHistories(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun createAccessHistory(createAccessHistory: CreateAccessHistory): Resource<CudResponse> {
        val response = try {
            api.createAccessHistory(createAccessHistory)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }
}