package com.example.idoor.data.remote.responses

data class  History(
    val alat: AlatX,
    val alat_id: Int,
    val created_at: String,
    val id: Int,
    val updated_at: String,
    val user: User,
    val user_id: Int,
    val waktu_akses: String,
    val device_model: String,
    val imei: String,
    val status_pintu: String,
    val status_akses: String,
)