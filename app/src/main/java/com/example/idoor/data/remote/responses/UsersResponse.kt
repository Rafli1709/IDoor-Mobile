package com.example.idoor.data.remote.responses

data class UsersResponse(
    val totalUser: Int,
    val users: List<User>
)