package com.example.idoor.data.remote.responses

data class AccessRightResponse(
    val alat: List<AlatXX>,
    val totalAlatDimiliki: Int,
    val totalAlatDipinjam: Int,
)