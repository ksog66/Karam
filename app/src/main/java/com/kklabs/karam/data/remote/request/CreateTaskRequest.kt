package com.kklabs.karam.data.remote.request

import com.squareup.moshi.Json

data class CreateTaskRequest(
    val title: String,
    val icon: String,
    val color: String,
    @Json(name = "date_created")
    val dateCreated: Long,
    @Json(name = "date_modified")
    val dateModified: Long? = null
)
