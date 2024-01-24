package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModuleData<T>(
    @Json(name = "module_id")
    val moduleId: String,
    val data: T
)