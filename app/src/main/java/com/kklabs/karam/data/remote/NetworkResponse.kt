package com.kklabs.karam.data.remote

sealed class NetworkResponse<out T : Any> {

    /**
     * Success response with body
     */
    data class Success<T : Any>(
        val body: T?
    ) : NetworkResponse<T>()

    /**
     * Error response with body
     */
    data class Error(val body: ErrorResponse) : NetworkResponse<Nothing>()

    val successBody: T
        get() = if (this is Success) {
            body ?: throw kotlin.Error("body is null")
        } else {
            throw kotlin.Error("Response is not success")
        }

}