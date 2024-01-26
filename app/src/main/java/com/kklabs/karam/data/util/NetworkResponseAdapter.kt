package com.kklabs.karam.data.util

import com.kklabs.karam.data.remote.ErrorResponse
import com.kklabs.karam.data.remote.NetworkResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

internal class NetworkResponseAdapter<S: Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, ErrorResponse>
): CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }

}