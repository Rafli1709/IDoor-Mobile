package com.example.idoor.data.models.accessright

data class AccessRightEntry (
    val id: Int,
    val toolName: String,
    val status: String,
    val timeLimit: String?
)