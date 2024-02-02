package com.kklabs.karam.data.repo

import com.kklabs.karam.domain.DataSource
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.remote.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun createUser(request: CreateUserRequest): NetworkResponse<UserResponse> {
        return dataSource.createUser(request)
    }

}