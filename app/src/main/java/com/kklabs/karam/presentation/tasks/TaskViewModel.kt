package com.kklabs.karam.presentation.tasks

import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.repo.TaskRepository
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel() {


    suspend fun createTask(request: CreateTaskRequest) = launchIO({

    }) {

    }

    suspend fun getTasks() = launchIO({

    }) {

    }
}