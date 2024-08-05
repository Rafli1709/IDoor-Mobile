package com.example.idoor.data.remote.responses

data class DetailAccessRightResponseItem(
    val alat_id: Int,
    val batas_waktu: String,
    val created_at: String,
    val id: Int,
    val updated_at: String,
    val user: User,
    val user_id: Int
)