package com.kklabs.karam.domain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kklabs.karam.data.mapper.toLogDateViewData
import com.kklabs.karam.data.mapper.toTasklogViewData
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.repo.TasklogsRepository
import com.kklabs.karam.domain.model.TasklogsComponentViewData

class TasklogsPagingSource(
    private val taskId: Int?,
    private val tasklogsRepo: TasklogsRepository
) : PagingSource<Int, TasklogsComponentViewData>() {

    override fun getRefreshKey(state: PagingState<Int, TasklogsComponentViewData>): Int? {
        return 0;
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TasklogsComponentViewData> {
        return try {
            val page = params.key ?: 0
            if (taskId == null) throw Exception("TaskId is invalid")
            when (val response = tasklogsRepo.getTasklogs(taskId, page)) {
                is NetworkResponse.Error -> {
                    LoadResult.Error(Exception(response.body.message))
                }

                is NetworkResponse.Success -> {
                    val tasklogsList = mutableListOf<TasklogsComponentViewData>()
                    response.successBody.data.forEach { moduleData ->
                        when (moduleData.moduleId) {
                            "task_logs" -> {
                                val taskLog =
                                    (moduleData.data as LogEntity.TasklogEntity).toTasklogViewData()
                                tasklogsList.add(taskLog)
                            }

                            "log_date" -> {
                                val logDate =
                                    (moduleData.data as LogEntity.LogDateEntity).toLogDateViewData()
                                tasklogsList.add(logDate)
                            }
                        }
                    }
                    LoadResult.Page(
                        data = tasklogsList,
                        prevKey = null,
                        nextKey = if (response.successBody.data.isNotEmpty()) response.successBody.paginationKey else null
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}