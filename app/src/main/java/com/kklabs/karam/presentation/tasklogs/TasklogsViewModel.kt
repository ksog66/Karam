package com.kklabs.karam.presentation.tasklogs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kklabs.karam.data.mapper.toTasklogViewData
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.repo.TasklogsRepository
import com.kklabs.karam.domain.model.TasklogViewData
import com.kklabs.karam.presentation.base.BaseViewModel
import com.kklabs.karam.presentation.navigation.TASK_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "TasklogsViewModel"

@HiltViewModel
class TasklogsViewModel @Inject constructor(
    private val tasklogsRepository: TasklogsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val taskId: Int? = savedStateHandle[TASK_ID_KEY]

    var tasklogsPager: Flow<PagingData<TasklogViewData>>

    private val _uiState = MutableStateFlow(TasklogsUiState())
    val uiState: StateFlow<TasklogsUiState> = _uiState

    init {
        try {
            if (taskId != null) {
                tasklogsPager = tasklogsRepository.getTasklogs(taskId)
                    .map { pagingData ->
                        pagingData.map { tasklogDbEntity ->
                            tasklogDbEntity.toTasklogViewData()
                        }
                    }.cachedIn(viewModelScope)
            } else {
                _uiState.value = TasklogsUiState(errorMessage = "TaskId invalid")
                tasklogsPager = flow { PagingData.empty<TasklogViewData>() }
            }
        } catch (exception: Exception) {
            tasklogsPager = flow { PagingData.empty<TasklogViewData>() }
            _uiState.value = TasklogsUiState(errorMessage = exception.message)
        }

    }

    fun createTasklogs(message: String) = launchIO {
        try {
            val request = CreateTasklogRequest(
                content = message,
                type = "TEXT_MESSAGE",
                taskId = taskId!!,
                dateCreated = System.currentTimeMillis()
            )
            when (val res = tasklogsRepository.createTasklog(request)) {
                is NetworkResponse.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = res.body.message, isLogAdded = false)
                    }
                }

                is NetworkResponse.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = null, isLogAdded = true)
                    }

                    tasklogsRepository.addTasklogs(res.successBody)
                }
            }
        } catch (e: Exception) {
            _uiState.update { currentState ->
                currentState.copy(errorMessage = e.message)
            }
        }
    }

    fun resetUiState() = launchIO {
        _uiState.value = TasklogsUiState()
    }
}

data class TasklogsUiState(
    val isLogAdded: Boolean? = null,
    val errorMessage: String? = null,
)