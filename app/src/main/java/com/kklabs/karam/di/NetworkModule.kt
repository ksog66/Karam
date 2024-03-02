package com.kklabs.karam.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kklabs.karam.BuildConfig
import com.kklabs.karam.Constants.SIGN_IN_REQUEST
import com.kklabs.karam.Constants.SIGN_UP_REQUEST
import com.kklabs.karam.R
import com.kklabs.karam.data.local.KaramDB
import com.kklabs.karam.data.remote.NetworkApi
import com.kklabs.karam.data.remote.CommonHeadersInterceptor
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.remote.response.LogType
import com.kklabs.karam.data.util.NetworkResponseAdapterFactory
import com.kklabs.karam.data.util.adapter.CustomDateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build())
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build())
        .build()

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(CustomDateAdapter())
            .add(PolymorphicJsonAdapterFactory.of(LogEntity::class.java, "log_type")
                .withSubtype(LogEntity.TasklogEntity::class.java, LogType.TASK_LOG.mName)
                .withSubtype(LogEntity.LogDateEntity::class.java, LogType.LOG_DATE.mName)
            )
            .add(KotlinJsonAdapterFactory())
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
            .readTimeout(1L, TimeUnit.MINUTES)
            .connectTimeout(1L, TimeUnit.MINUTES)
            .writeTimeout(1L, TimeUnit.MINUTES)
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

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideSportifyDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, KaramDB::class.java, "karam.db")
            .build()
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitStore