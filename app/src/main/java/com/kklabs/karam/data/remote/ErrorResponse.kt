package com.kklabs.karam.data.remote

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String,
    val statusCode: Int,
    val code: String? = null
) {

    fun error() = message

}