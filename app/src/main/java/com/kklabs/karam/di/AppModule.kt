package com.kklabs.karam.di

import android.content.Context
import com.kklabs.karam.data.DataSource
import com.kklabs.karam.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(dataSource: RemoteDataSource): DataSource
}