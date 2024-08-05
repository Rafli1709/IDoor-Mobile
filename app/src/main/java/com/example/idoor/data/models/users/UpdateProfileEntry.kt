package com.example.idoor.data.models.users

import com.google.gson.annotations.SerializedName

data class UpdateProfileEntry(
    val name: String,
    val username: String,
    val email: String,
    @SerializedName("no_hp") val phoneNumber: String,
    @SerializedName("jenis_kelamin") val gender: String,
    @SerializedName("tgl_lahir") val dateBirth: String,
    @SerializedName("alamat") val address: String,
)
