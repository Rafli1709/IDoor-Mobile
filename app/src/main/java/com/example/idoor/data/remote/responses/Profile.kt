package com.example.idoor.data.remote.responses

data class Profile(
    val alamat: String,
    val created_at: String,
    val id: Int,
    val jenis_kelamin: String,
    val no_hp: String,
    val tgl_lahir: String,
    val updated_at: String,
    val user_id: Int
)