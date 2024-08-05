package com.example.idoor.data.remote.responses

data class Alat(
    val created_at: String,
    val id: Int,
    val imei: String,
    val nama: String,
    val password: String,
    val updated_at: String,
    val user: User,
    val user_id: Int
)