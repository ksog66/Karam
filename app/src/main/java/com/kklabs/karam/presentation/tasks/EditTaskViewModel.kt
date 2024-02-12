package com.kklabs.karam.presentation.tasks

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
    }

    fun updateTask(request: UpdateTaskRequest) = launchIO {
        when (val res = taskRepository.updateTask(request)) {
            is NetworkResponse.Error -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isEditSuccessful = false,
                        errorMessage = res.body.message
                    )
                }
            }
            is NetworkResponse.Success -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        isEditSuccessful = true
                    )
                }
            }
        }
    }

}

data class EditTaskUiState(
    val isLoading: Boolean = false,
    val isEditSuccessful: Boolean = false,
    val isTaskFetchSuccess: Boolean? = null,
    val errorMessage: String? = null,
    val task: Task? = null
)