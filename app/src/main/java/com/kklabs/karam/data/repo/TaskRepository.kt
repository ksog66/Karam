package com.kklabs.karam.data.repo

import com.kklabs.karam.data.DataSource
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.response.TaskResponse
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun createTask(request: CreateTaskRequest): NetworkResponse<TaskResponse> {
        return dataSource.createTask(request)
    }

    suspend fun getTasks(): NetworkResponse<Nothing> {
        return dataSource.getTasks()
    }

    suspend fun getTask(id: Long): NetworkResponse<TaskResponse> {
        return dataSource.getTask(id)
    }
}