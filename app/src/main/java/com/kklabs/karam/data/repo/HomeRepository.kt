package com.kklabs.karam.data.repo

import com.kklabs.karam.domain.DataSource
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.response.HomeDataResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun getHomeData(year: Int): NetworkResponse<HomeDataResponse> {
        return dataSource.getHomeData(year)
    }
}