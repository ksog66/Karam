package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TasksKaram(
    @Json(name = "color")
    val color: String,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "karam")
    val karam: Map<Long, Int>,
    @Json(name = "name")
    val name: String
)