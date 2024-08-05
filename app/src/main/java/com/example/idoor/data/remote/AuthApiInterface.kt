package com.example.idoor.data.remote

import com.example.idoor.data.models.accesshistory.CreateAccessHistory
import com.example.idoor.data.models.accessright.CreateAccessRight
import com.example.idoor.data.models.tools.CreateToolEntry
import com.example.idoor.data.models.users.CreateUserEntry
import com.example.idoor.data.models.users.UpdateProfileEntry
import com.example.idoor.data.models.users.UpdateUserEntry
import com.example.idoor.data.remote.responses.AccessHistoryResponse
import com.example.idoor.data.remote.responses.AccessRightResponse
import com.example.idoor.data.remote.responses.Alat
import com.example.idoor.data.remote.responses.CudResponse
import com.example.idoor.data.remote.responses.DetailAccessRightResponse
import com.example.idoor.data.remote.responses.ProfileResponse
import com.example.idoor.data.remote.responses.ToolResponse
import com.example.idoor.data.remote.responses.User
import com.example.idoor.data.remote.responses.UsersResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiInterface {

    @GET("user")
    suspend fun getUsers(): UsersResponse

    @POST("user")
    suspend fun createUser(@Body createUserEntry: CreateUserEntry): CudResponse

    @GET("user/{user_id}")
    suspend fun getUserInfo(
        @Path("user_id") userId: Int
    ): User

    @PUT("user/{user_id}")
    suspend fun updateUser(
        @Path("user_id") userId: Int,
        @Body updateUserEntry: UpdateUserEntry
    ): CudResponse

    @DELETE("user/{user_id}")
    suspend fun deleteUser(
        @Path("user_id") userId: Int
    ): CudResponse

    @GET("alat")
    suspend fun getTools(): ToolResponse

    @POST("alat")
    suspend fun createTool(@Body createToolEntry: CreateToolEntry): CudResponse

    @GET("alat/{alat_id}")
    suspend fun getToolInfo(
        @Path("alat_id") toolId: Int
    ): Alat

    @PUT("alat/{alat_id}")
    suspend fun updateTool(
        @Path("alat_id") toolId: Int,
        @Body createToolEntry: CreateToolEntry
    ): CudResponse

    @DELETE("alat/{alat_id}")
    suspend fun deleteTool(
        @Path("alat_id") toolId: Int
    ): CudResponse

    @GET("hak-akses/{user_id}")
    suspend fun getAccessRights(
        @Path("user_id") userId: Int,
    ): AccessRightResponse

    @POST("hak-akses")
    suspend fun createAccessRight(@Body createAccessRight: CreateAccessRight): CudResponse

    @GET("hak-akses/{alat_id}/show")
    suspend fun getAccessRightInfo(
        @Path("alat_id") toolId: Int
    ): DetailAccessRightResponse

    @DELETE("hak-akses/{hak_akses_id}")
    suspend fun deleteAccessRight(
        @Path("hak_akses_id") accessRightId: Int
    ): CudResponse

    @GET("history-akses/{user_id}")
    suspend fun getAccessHistories(
        @Path("user_id") userId: Int,
    ): AccessHistoryResponse

    @POST("history-akses")
    suspend fun createAccessHistory(@Body createAccessHistory: CreateAccessHistory): CudResponse

    @PUT("profile/{user_id}")
    suspend fun updateProfile(
        @Path("user_id") userId: Int,
        @Body updateProfileEntry: UpdateProfileEntry
    ): ProfileResponse
}