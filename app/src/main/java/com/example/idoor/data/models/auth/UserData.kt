package com.example.idoor.data.models.auth

data class UserData(
    val name: String,
    val username: String,
    val email: String,
    val role: String,
    val phoneNumber: String,
    val gender: String,
    val dateBirth: String,
    val address: String,
    val id: Int
)
