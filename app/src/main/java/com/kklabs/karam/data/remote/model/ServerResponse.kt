package com.kklabs.karam.data.remote.model

import androidx.annotation.Keep

@Keep
data class ServerResponse(
    val code: String,
    val message: String?
) {

    val isSuccessful: Boolean
        get() = code == Code.SUCCESS

    fun success() = code == Code.SUCCESS

}