package com.example.idoor.data.models.tools

import com.google.gson.annotations.SerializedName

data class CreateToolEntry(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("nama") val name: String,
    @SerializedName("secret_key") val plaintext: String,
    val imei: String,
)
