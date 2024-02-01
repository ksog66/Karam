package com.kklabs.karam.presentation.tasklogs

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.insertHeaderItem
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val _uiState = MutableStateFlow<TasklogsUiState>(TasklogsUiState())
    val uiState: StateFlow<TasklogsUiState> = _uiState

    fun createTasklogs(message: String) = launchIO({

    }) {
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