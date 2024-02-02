package com.kklabs.karam.di

import com.kklabs.karam.domain.DataSource
import com.kklabs.karam.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(dataSource: RemoteDataSource): DataSource
}