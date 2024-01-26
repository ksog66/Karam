package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class TaskResponse(
    val color: String,
    @Json(name = "date_created")
    val dateCreated: Date,
    @Json(name = "date_modified")
    val dateModified: Date?,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "user_id")
    val userId: Int
)