package com.kklabs.karam.data.remote

import com.kklabs.karam.data.remote.model.ServerResponse
import com.kklabs.karam.domain.DataSource
import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.remote.request.UpdateTaskRequest
import com.kklabs.karam.data.remote.response.DataResponse
import com.kklabs.karam.data.remote.response.HomeDataResponse
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.remote.response.ModuleData
import com.kklabs.karam.data.remote.response.TaskResponse
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.data.remote.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: NetworkApi
) : DataSource {

    override suspend fun getHomeData(year: Int): NetworkResponse<HomeDataResponse> {
        return when (val res = api.getHomeData(year)) {
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

    override suspend fun updateTask(id: Int, request: UpdateTaskRequest): NetworkResponse<TaskResponse> {
        return when (val res = api.updateTask(id, request)) {
            is NetworkResponse.Error -> {
                res
            }

            is NetworkResponse.Success -> {
                NetworkResponse.Success(res.body)
            }
        }
    }

    override suspend fun deleteTask(id: Int): ServerResponse {
        return api.deleteTask(id)
    }

    override suspend fun getTask(id: Int): NetworkResponse<TaskResponse> {
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
        taskId: Int,
        pageKey: Int?
    ): NetworkResponse<DataResponse<List<ModuleData<LogEntity>>>> {
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