package com.kklabs.karam.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kklabs.karam.BuildConfig
import com.kklabs.karam.data.remote.NetworkApi
import com.kklabs.karam.data.remote.CommonHeadersInterceptor
import com.kklabs.karam.data.util.NetworkResponseAdapterFactory
import com.kklabs.karam.data.util.adapter.CustomDateAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(CustomDateAdapter())
            .build()
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
        chuckerInterceptor: ChuckerInterceptor,
        commonHeadersInterceptor: CommonHeadersInterceptor
        //authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(commonHeadersInterceptor)
            addInterceptor(chuckerInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
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
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitStore