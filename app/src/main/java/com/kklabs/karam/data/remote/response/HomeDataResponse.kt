package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeDataResponse(
    @Json(name = "cumulative_karam")
    val cumulativeKaram: Map<String, Int>,
    @Json(name = "tasks_karam")
    val tasksKaram: List<TasksKaram>
)