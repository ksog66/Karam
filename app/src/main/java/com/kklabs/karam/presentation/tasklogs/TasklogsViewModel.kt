package com.kklabs.karam.presentation.tasklogs

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.kklabs.karam.data.mapper.toLogDateViewData
import com.kklabs.karam.data.mapper.toTasklogViewData
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.repo.TasklogsRepository
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

    private val _uiState = MutableStateFlow<TasklogsUiState>(TasklogsUiState())
    val uiState: StateFlow<TasklogsUiState> = _uiState

    private var currentPaginationKey: Int? = 0


    init {
        if (taskId != null) {
            getTasklogs(taskId)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isTaskIdValid = false)
            }
        }
    }

    suspend fun createTasklogs(request: CreateTasklogRequest) = launchIO({

    }) {

    }

    fun getTasklogs(taskId: Int) = launchIO({

    }) {
        try {
            when (val res = tasklogsRepository.getTasklogs(taskId, currentPaginationKey)) {
                is NetworkResponse.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = res.body.message)
                    }
                }

                is NetworkResponse.Success -> {
                    val tasklogsList = mutableListOf<TasklogsComponentViewData>()
                    tasklogsList.addAll(_uiState.value.feed)
                    res.successBody.data.forEach { moduleData ->
                        Log.d(TAG, """
                            moduleData -> $moduleData
                        """.trimIndent())
                        when (moduleData.moduleId) {
                            "task_logs" -> {
                                val taskLog =
                                    (moduleData.data as LogEntity.TasklogEntity).toTasklogViewData()
                                tasklogsList.add(taskLog)
                            }

                            "log_date" -> {
                                val logDate = (moduleData.data as LogEntity.LogDateEntity).toLogDateViewData()
                                tasklogsList.add(logDate)
                            }
                        }
                    }
                    _uiState.update { currentState ->
                        currentState.copy(feed = tasklogsList)
                    }
                    currentPaginationKey = res.successBody.paginationKey
                }
            }
        } catch (e: Exception) {
            Log.e(
                TAG,
                """
                errorMessage while fetching tasklogs-> ${e.message}
            """.trimIndent(),
            )
            _uiState.update { currentState ->
                currentState.copy(errorMessage = e.message)
            }
        }

    }
}

data class TasklogsUiState(
    val feed: List<TasklogsComponentViewData> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isTaskIdValid: Boolean = true
)