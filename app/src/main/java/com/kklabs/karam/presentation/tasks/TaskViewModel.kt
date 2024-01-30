package com.kklabs.karam.presentation.tasks

import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.response.TaskResponse
import com.kklabs.karam.data.repo.TaskRepository
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<CreateTaskUiState>(CreateTaskUiState.Idle)
    val uiState: StateFlow<CreateTaskUiState> get() = _uiState


    fun createTask(request: CreateTaskRequest) = launchIO({

    }) {
        try {
            when (val res = taskRepository.createTask(request)) {
                is NetworkResponse.Error -> {
                    _uiState.update { _ ->
                        CreateTaskUiState.Error(res.body.message)
                    }
                }
                is NetworkResponse.Success -> {
                    _uiState.update { _ ->
                        CreateTaskUiState.Success(res.successBody)
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update { _ ->
                CreateTaskUiState.Error(e.localizedMessage)
            }
        }
    }

    fun resetState() {
        _uiState.update {
            CreateTaskUiState.Idle
        }
    }
}

sealed interface CreateTaskUiState {
    object Idle : CreateTaskUiState

    data class Success(val task: TaskResponse) : CreateTaskUiState

    data class Error(val message: String?) : CreateTaskUiState
}