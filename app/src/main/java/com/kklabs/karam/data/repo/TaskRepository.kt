package com.kklabs.karam.data.repo

import com.kklabs.karam.domain.DataSource
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.model.ServerResponse
import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.request.UpdateTaskRequest
import com.kklabs.karam.data.remote.response.TaskResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun createTask(request: CreateTaskRequest): NetworkResponse<TaskResponse> {
        return dataSource.createTask(request)
    }

    suspend fun updateTask(id: Int, request: UpdateTaskRequest): NetworkResponse<TaskResponse> {
        return dataSource.updateTask(id, request)
    }

    suspend fun getTasks(): NetworkResponse<Nothing> {
        return dataSource.getTasks()
    }

    suspend fun getTask(id: Int): NetworkResponse<TaskResponse> {
        return dataSource.getTask(id)
    }

    suspend fun deleteTask(id: Int): NetworkResponse<ServerResponse> {
        return dataSource.deleteTask(id)
    }
}