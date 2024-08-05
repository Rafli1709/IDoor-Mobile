package com.example.idoor.data.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterEntry (
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation") val confirmationPassword: String,
)