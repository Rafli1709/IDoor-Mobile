package com.example.idoor.repository

import com.example.idoor.data.models.tools.CreateToolEntry
import com.example.idoor.data.remote.AuthApiInterface
import com.example.idoor.data.remote.responses.Alat
import com.example.idoor.data.remote.responses.CudResponse
import com.example.idoor.data.remote.responses.ToolResponse
import com.example.idoor.util.Resource
import javax.inject.Inject

class ToolRepository @Inject constructor(
    private val api: AuthApiInterface
){
    suspend fun getTools(): Resource<ToolResponse> {
        val response = try {
            api.getTools()
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun createTool(createToolEntry: CreateToolEntry): Resource<CudResponse> {
        val response = try {
            api.createTool(createToolEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun getToolInfo(id: Int): Resource<Alat> {
        val response = try {
            api.getToolInfo(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun updateTool(id: Int, createToolEntry: CreateToolEntry): Resource<CudResponse> {
        val response = try {
            api.updateTool(id, createToolEntry)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }

    suspend fun deleteTool(id: Int): Resource<CudResponse> {
        val response = try {
            api.deleteTool(id)
        } catch (e: Exception) {
            return Resource.handleApiError(e)
        }
        return Resource.Success(response)
    }
}