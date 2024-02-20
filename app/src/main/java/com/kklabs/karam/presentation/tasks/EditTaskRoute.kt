package com.kklabs.karam.presentation.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.R
import com.kklabs.karam.util.showShortToast

@Composable
fun EditTaskRoute(
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = hiltViewModel(),
    navigateBack: (forceFetch: Boolean) -> Unit
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val showLoader = rememberSaveable { (mutableStateOf(true)) }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            showShortToast(context, it)
        }
    }

    LaunchedEffect(uiState.isEditSuccessful) {
        uiState.isTaskFetchSuccess?.let {
            if (it) {
                navigateBack(true)
            }
        }
    }

    LaunchedEffect(uiState.isDeleteTaskSuccess) {
        uiState.isDeleteTaskSuccess?.let {
            if (it) {
                showShortToast(context, "Task Successfully deleted.")
                navigateBack(true)
            } else {
                showShortToast(context, "Failed to delete the task! Try again")
            }
        }
    }

    LaunchedEffect(uiState.isLoading) {
        showLoader.value = uiState.isLoading
    }


    when {
        uiState.task != null -> {
            AddOrEditTaskScreen(
                modifier = modifier.fillMaxSize(),
                task = uiState.task,
                showLoader = showLoader,
                updateTask = viewModel::updateTask,
                deleteTask = viewModel::deleteTask,
                navigateBack = {
                    navigateBack.invoke(false)
                }
            )
        }
    }


}