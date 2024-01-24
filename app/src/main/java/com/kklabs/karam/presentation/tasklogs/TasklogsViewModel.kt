package com.kklabs.karam.presentation.tasklogs

import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.repo.TasklogsRepository
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasklogsViewModel @Inject constructor(
    private val tasklogsRepository: TasklogsRepository
) : BaseViewModel() {

    private var currentPaginationKey: String? = null

    suspend fun createTasklogs(request: CreateTasklogRequest) = launchIO({

    }) {

    }

    suspend fun getTasklogs(taskId: Long) = launchIO({

    }) {

    }
}