package com.kklabs.karam.data.remote.request

import com.squareup.moshi.Json

data class CreateTasklogRequest(
    val content: String,
    val type: String,
    @Json(name = "task_id")
    val taskId: Int,
    @Json(name = "date_created")
    val dateCreated: Long
)