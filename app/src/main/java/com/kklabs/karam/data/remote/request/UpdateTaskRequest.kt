package com.kklabs.karam.data.remote.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateTaskRequest(
    val title: String?,
    val icon: String?,
    val color: String?
)