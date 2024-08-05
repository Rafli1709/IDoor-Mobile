package com.example.idoor.data.models.accesshistory

import com.google.gson.annotations.SerializedName

data class CreateAccessHistory(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("imei_alat") val imeiTool: String,
    @SerializedName("status_pintu") val doorStatus: String,
    val password: String,
    @SerializedName("imei_akses") val imeiAccess: String,
    @SerializedName("device_model") val deviceModel: String,
)
