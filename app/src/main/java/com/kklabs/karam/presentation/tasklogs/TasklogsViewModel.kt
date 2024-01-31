package com.kklabs.karam.presentation.tasklogs

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kklabs.karam.data.mapper.toLogDateViewData
import com.kklabs.karam.data.mapper.toTasklogViewData
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.repo.TasklogsRepository
import com.kklabs.karam.domain.TasklogsPagingSource
import com.kklabs.karam.domain.model.TasklogsComponentViewData
import com.kklabs.karam.presentation.base.BaseViewModel
import com.kklabs.karam.presentation.navigation.TASK_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "TasklogsViewModel"

@HiltViewModel
class TasklogsViewModel @Inject constructor(
    private val tasklogsRepository: TasklogsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val taskId: Int? = savedStateHandle[TASK_ID_KEY]

    val tasklogsPager = Pager(
        PagingConfig(pageSize = 10)
    ) {
        TasklogsPagingSource(taskId = taskId, tasklogsRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun createTasklogs(request: CreateTasklogRequest) = launchIO({

    }) {

    }
}

data class TasklogsUiState(
    val feed: List<TasklogsComponentViewData> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isTaskIdValid: Boolean = true
)