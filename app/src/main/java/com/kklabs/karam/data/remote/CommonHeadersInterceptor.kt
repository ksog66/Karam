package com.kklabs.karam.data.remote

import android.app.Application
import android.content.Context
import com.kklabs.karam.data.ds.ConfigPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CommonHeadersInterceptor @Inject constructor(
    private val context: Context,
    private val configPreferences: ConfigPreferences
) : Interceptor {

    private val packageInfo by lazy {
        (context as Application).packageManager.getPackageInfo(context.packageName, 0)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader(CONTENT_TYPE, "application/json;charset=utf-8")
            requestBuilder.addHeader(CONNECTION, "keep-alive")
            requestBuilder.addHeader(CONTENT_ACCEPT, "application/json")
//            requestBuilder.addHeader(API_KEY, BuildConfig.API_KEY)
            requestBuilder.addHeader(AUTHORIZATION, "Bearer ${configPreferences.getAuthToken()}")
            requestBuilder.addHeader(APP_CLIENT, "consumer-android")
            requestBuilder.addHeader(APP_IDENTIFIER, configPreferences.getApplicationPackage())
            requestBuilder.addHeader(APP_VERSION_CODE, packageInfo.versionCode.toString())
            requestBuilder.addHeader(APP_VERSION_NAME, packageInfo.versionName)
            requestBuilder.addHeader(CLIENT_TIMESTAMP, System.currentTimeMillis().toString())
            return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONNECTION = "Connection"
        private const val CONTENT_ACCEPT = "Accept"
        private const val API_KEY = "x-api-key"
        private const val AUTHORIZATION = "Authorization"
        private const val APP_CLIENT = "app-client"
        private const val APP_IDENTIFIER = "app-identifier"
        private const val APP_VERSION_CODE = "app-version-code"
        private const val APP_VERSION_NAME = "app-version-name"
        private const val CLIENT_TIMESTAMP = "client-ts"
    }

}