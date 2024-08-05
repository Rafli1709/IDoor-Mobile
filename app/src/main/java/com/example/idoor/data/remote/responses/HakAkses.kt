package com.example.idoor.data.remote.responses

data class HakAkses(
    val alat_id: Int,
    val batas_waktu: String,
    val created_at: String,
    val id: Int,
    val updated_at: String,
    val user_id: Int,
    val user: User,
    val alat: AlatX
)