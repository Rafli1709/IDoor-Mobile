package com.example.idoor.data.remote

import com.example.idoor.data.models.auth.LoginEntry
import com.example.idoor.data.models.auth.RegisterEntry
import com.example.idoor.data.remote.responses.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GuestApiInterface {

    @POST("login")
    suspend fun login(@Body loginEntry: LoginEntry): AuthResponse

    @POST("register")
    suspend fun register(@Body registerEntry: RegisterEntry): AuthResponse
}