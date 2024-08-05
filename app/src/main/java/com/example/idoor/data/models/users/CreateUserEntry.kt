package com.example.idoor.data.models.users

import com.google.gson.annotations.SerializedName

data class CreateUserEntry (
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation") val confirmationPassword: String,
)