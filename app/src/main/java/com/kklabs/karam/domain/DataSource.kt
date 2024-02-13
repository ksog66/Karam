package com.kklabs.karam.domain

import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.model.ServerResponse
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

interface DataSource {

    suspend fun getHomeData(year: Int): NetworkResponse<HomeDataResponse>

    suspend fun createUser(request: CreateUserRequest): NetworkResponse<UserResponse>

    suspend fun createTask(request: CreateTaskRequest): NetworkResponse<TaskResponse>

    suspend fun updateTask(id: Int, request: UpdateTaskRequest): NetworkResponse<TaskResponse>

    suspend fun deleteTask(id: Int): NetworkResponse<ServerResponse>

    suspend fun getTask(id: Int): NetworkResponse<TaskResponse>

    suspend fun getTasks(): NetworkResponse<Nothing>

    suspend fun createTasklog(request: CreateTasklogRequest): NetworkResponse<TasklogResponse>

    suspend fun getTasklogs(
        taskId: Int,
        pageKey: Int? = null
    ): NetworkResponse<DataResponse<List<ModuleData<LogEntity>>>>

}
