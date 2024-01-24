package com.kklabs.karam.data.remote.request

data class CreateUserRequest(
    val name: String,
    val email: String,
    val username: String
)
