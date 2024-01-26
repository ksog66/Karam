package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class TasklogResponse(
    @Json(name = "content")
    val content: String,
    @Json(name = "date_created")
    val dateCreated: Date,
    @Json(name = "id")
    val id: Int,
    @Json(name = "task_id")
    val taskId: Int,
    @Json(name = "type")
    val type: String,
    @Json(name = "user_id")
    val userId: Int
)

@JsonClass(generateAdapter = true)
data class LogDate(
    val date: Long
)