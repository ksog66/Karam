package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskResponse(
    val color: String,
    @Json(name = "date_created")
    val dateCreated: Long,
    @Json(name = "date_modified")
    val dateModified: Long,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "user_id")
    val userId: Int
)