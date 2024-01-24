package com.kklabs.karam.data.util

import com.kklabs.karam.data.remote.ErrorResponse
import com.kklabs.karam.data.remote.NetworkResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type

private const val DEFAULT_ERR_MESSAGE = "Unknown error occurred"

internal class NetworkResponseCall<S : Any>(
    private val moshi: Moshi,
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, ErrorResponse>
) : Call<NetworkResponse<S>> {

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                try {
                    val body = response.body()
                    val code = response.code()
                    val error = response.errorBody()

                    if (response.isSuccessful) {
                        callback.onResponse(
                            this@NetworkResponseCall, Response.success(
                                NetworkResponse.Success(body)
                            )
                        )
                    } else {
                        val errorBody = when {
                            error == null -> null
                            error.contentLength() == 0L -> null
                            else -> try {
                                errorConverter.convert(error)
                            } catch (ex: Exception) {
                                null
                            }
                        }
                        if (errorBody != null) {
                            callback.onResponse(
                                this@NetworkResponseCall, Response.success(
                                    NetworkResponse.Error(errorBody.copy(statusCode = code))
                                )
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall, Response.success(
                                    NetworkResponse.Error(
                                        ErrorResponse(
                                            DEFAULT_ERR_MESSAGE, code
                                        )
                                    )
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    callback.onResponse(
                        this@NetworkResponseCall, Response.success(
                            NetworkResponse.Error(
                                ErrorResponse(
                                    e.message ?: DEFAULT_ERR_MESSAGE, -1
                                )
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.Error(
                        ErrorResponse(
                            throwable.message ?: DEFAULT_ERR_MESSAGE,
                            -1,
                        )
                    )

                    else -> NetworkResponse.Error(
                        ErrorResponse(
                            throwable.message ?: DEFAULT_ERR_MESSAGE, -1
                        )
                    )
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(moshi, delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}
