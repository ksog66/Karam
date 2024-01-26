package com.kklabs.karam.data.remote.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateUserRequest(
    val name: String,
    val email: String,
    val username: String
)
