package com.kklabs.karam.di

import com.kklabs.karam.BuildConfig
import com.kklabs.karam.data.remote.NetworkApi
import com.kklabs.karam.data.remote.CommonHeadersInterceptor
import com.kklabs.karam.data.util.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideApiV1(@RetrofitStore retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(client: OkHttpClient): OkHttpClient.Builder {
        return client.newBuilder()
            .readTimeout(10L, TimeUnit.SECONDS)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    fun provideBaseOkHttpClient(
        commonHeadersInterceptor: CommonHeadersInterceptor
        //authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(commonHeadersInterceptor)
        }.build()
    }

    @Provides
    @RetrofitStore
    @Singleton
    fun provideRetrofit(
        clientBuilder: OkHttpClient.Builder,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(moshi))
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitStore