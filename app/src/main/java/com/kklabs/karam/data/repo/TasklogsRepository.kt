package com.kklabs.karam.data.repo

import com.kklabs.karam.data.DataSource
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.response.DataResponse
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.remote.response.ModuleData
import com.kklabs.karam.data.remote.response.TasklogResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasklogsRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun getTasklogs(taskId: Int, pageKey: Int?): NetworkResponse<DataResponse<List<ModuleData<LogEntity>>>> {
        return dataSource.getTasklogs(taskId, pageKey)
    }

    suspend fun createTasklog(request: CreateTasklogRequest): NetworkResponse<TasklogResponse> {
        return dataSource.createTasklog(request)
    }
}