package com.kklabs.karam.data.repo

import com.kklabs.karam.data.DataSource
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.remote.response.UserResponse
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun createUser(request: CreateUserRequest): NetworkResponse<UserResponse> {
        return dataSource.createUser(request)
    }

}