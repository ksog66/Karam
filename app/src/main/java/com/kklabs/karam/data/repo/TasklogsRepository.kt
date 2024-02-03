package com.kklabs.karam.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kklabs.karam.domain.DataSource
import com.kklabs.karam.data.local.KaramDB
import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.data.mapper.toTasklogDbEntity
import com.kklabs.karam.data.mapper.toTasklogViewData
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.domain.TasklogsRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasklogsRepository @Inject constructor(
    private val dataSource: DataSource,
    private val db: KaramDB
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getTasklogs(taskId: Int): Flow<PagingData<TasklogDbEntity>> {
        val pagingSourceFactory = { db.tasklogsDao().getTasklogs(taskId) }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = TasklogsRemoteMediator(
                taskId,
                dataSource,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun createTasklog(request: CreateTasklogRequest): NetworkResponse<TasklogResponse> {
        return dataSource.createTasklog(request)
    }

    suspend fun addTasklogs(tasklogs: TasklogResponse) {
        val tasklogDbEntity = tasklogs.toTasklogDbEntity()
        db.tasklogsDao().insertOne(tasklogDbEntity)
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}