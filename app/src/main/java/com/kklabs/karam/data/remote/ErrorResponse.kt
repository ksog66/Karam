package com.kklabs.karam.data.remote

import androidx.annotation.Keep

@Keep
data class ErrorResponse(
    val message: String,
    val statusCode: Int,
    val code: String? = null
) {

    fun error() = message

}