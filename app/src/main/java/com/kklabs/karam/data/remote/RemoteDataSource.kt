package com.kklabs.karam.data.remote

import com.kklabs.karam.data.DataSource
import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.remote.response.DataResponse
import com.kklabs.karam.data.remote.response.HomeDataResponse
import com.kklabs.karam.data.remote.response.ModuleData
import com.kklabs.karam.data.remote.response.TaskResponse
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.data.remote.response.UserResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: NetworkApi
) : DataSource {

    override suspend fun getHomeData(): NetworkResponse<HomeDataResponse> {
        return when (val res = api.getHomeData()) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun createUser(request: CreateUserRequest): NetworkResponse<UserResponse> {
        return when (val res = api.createUser(request)) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun createTask(request: CreateTaskRequest): NetworkResponse<TaskResponse> {
        return when (val res = api.createTask(request)) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun getTask(id: Long): NetworkResponse<TaskResponse> {
        return when (val res = api.getTask(id)) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun getTasks(): NetworkResponse<Nothing> {
        return when (val res = api.getTasks()) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun createTasklog(request: CreateTasklogRequest): NetworkResponse<TasklogResponse> {
        return when (val res = api.createTasklog(request)) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun getTasklogs(
        taskId: Long,
        pageKey: Int?
    ): NetworkResponse<DataResponse<List<ModuleData<*>>>> {
        return when (val res = api.getTasklogs(taskId, pageKey)) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }
}