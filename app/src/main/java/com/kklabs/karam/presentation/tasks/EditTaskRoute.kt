package com.kklabs.karam.presentation.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.util.showShortToast

@Composable
fun EditTaskRoute(
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = hiltViewModel(),
    navigateBack: (forceFetch: Boolean) -> Unit
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            showShortToast(context, it)
        }
    }

    LaunchedEffect(uiState.isEditSuccessful) {
        uiState.isTaskFetchSuccess?.let {
            if (it) {
                navigateBack(false)
            }
        }
    }


    when {
        uiState.isLoading -> {
            //showLoadingScreen
        }

        uiState.task != null -> {
            AddOrEditTaskScreen(
                modifier = modifier.fillMaxSize(),
                task = uiState.task,
                updateTask = viewModel::updateTask,
                navigateBack = {
                    navigateBack.invoke(false)
                }
            )
        }
    }


}