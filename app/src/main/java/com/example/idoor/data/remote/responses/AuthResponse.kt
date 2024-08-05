package com.example.idoor.data.remote.responses

data class AuthResponse(
    val token: String,
    val user: ProfileResponse
)