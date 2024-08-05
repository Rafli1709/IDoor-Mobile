package com.example.idoor.data.models.tools

data class ToolEntry(
    val id: Int,
    val userId: Int,
    val toolName: String,
    val emailUser: String,
    val imei: String,
    val password: String,
)
