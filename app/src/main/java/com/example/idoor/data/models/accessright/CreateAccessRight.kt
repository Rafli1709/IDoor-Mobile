package com.example.idoor.data.models.accessright

import com.google.gson.annotations.SerializedName

data class CreateAccessRight(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("alat_id") val alatId: Int,
    @SerializedName("batas_waktu") val timeLimit: String?
)
