package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "jwtToken")
    val jwtToken: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "user_date_created")
    val userDateCreated: Long,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "username")
    val username: String
)