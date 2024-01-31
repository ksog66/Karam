package com.kklabs.karam.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class DataResponse<T>(
    @Json(name = "page_key")
    val paginationKey: Int,
    val data: T
)