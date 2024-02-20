package com.kklabs.karam.presentation.tasks

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.kklabs.karam.data.mapper.toTask
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.UpdateTaskRequest
import com.kklabs.karam.data.repo.TaskRepository
import com.kklabs.karam.domain.model.Task
import com.kklabs.karam.presentation.base.BaseViewModel
import com.kklabs.karam.presentation.navigation.TASK_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "EditTaskViewModel"
@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val taskId: Int? = savedStateHandle[TASK_ID_KEY]

    private val _uiState = MutableStateFlow<EditTaskUiState>(EditTaskUiState(isLoading = true))
    val uiState: StateFlow<EditTaskUiState> = _uiState

    init {
        if (taskId != null) {
            fetchTaskDetails(taskId)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isTaskFetchSuccess = false)
            }
        }
    }


    private fun fetchTaskDetails(id: Int) = launchIO {
        try {
            when (val res = taskRepository.getTask(id)) {
                is NetworkResponse.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isTaskFetchSuccess = false,
                            errorMessage = res.body.message
                        )
                    }
                }
                is NetworkResponse.Success -> {
                    _uiState.update {
                        EditTaskUiState(
                            isLoading = false,
                            isTaskFetchSuccess = true,
                            task = res.successBody.toTask()
                        )
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update { currentState ->
                Log.e(TAG, "fetchTaskDetails: ", e)
                currentState.copy(errorMessage = e.message)
            }
        }
    }

    fun updateTask(id: Int, request: UpdateTaskRequest) = launchIO {
        try {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            when (val res = taskRepository.updateTask(id, request)) {
                is NetworkResponse.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isEditSuccessful = false,
                            errorMessage = res.body.message
                        )
                    }
                }
                is NetworkResponse.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isEditSuccessful = true
                        )
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update { currentState ->
                currentState.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun deleteTask(id: Int) = launchIO {
        try {
            if (id < 0) {
                _uiState.update { currentState ->
                    currentState.copy(errorMessage = "Invalid task id")
                }
                return@launchIO
            }
            when (val res = taskRepository.deleteTask(id)) {
                is NetworkResponse.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = res.body.message)
                    }
                }
                is NetworkResponse.Success -> {
                    if (res.successBody.isSuccessful) {
                        _uiState.update { currentState ->
                            currentState.copy(isDeleteTaskSuccess = true)
                        }
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(isDeleteTaskSuccess = false)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update { currentState ->
                currentState.copy(errorMessage = e.message)
            }
        }
    }

}

data class EditTaskUiState(
    val isLoading: Boolean = false,
    val isEditSuccessful: Boolean = false,
    val isDeleteTaskSuccess: Boolean? = null,
    val isTaskFetchSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val task: Task? = null
)