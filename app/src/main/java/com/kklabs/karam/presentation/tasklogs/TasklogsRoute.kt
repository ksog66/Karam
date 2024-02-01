package com.kklabs.karam.presentation.tasklogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kklabs.karam.domain.model.TasklogsComponentViewData
import com.kklabs.karam.presentation.components.LogDateComponent
import com.kklabs.karam.presentation.components.TaskLogInput
import com.kklabs.karam.presentation.components.TasklogsComponent
import com.kklabs.karam.presentation.components.TextH20
import com.kklabs.karam.util.showShortToast

@Composable
fun TasklogsRoute(
    modifier: Modifier = Modifier,
    taskName: String,
    navigateBack: () -> Unit,
    viewModel: TasklogsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val tasklogsList = viewModel.tasklogsPager.collectAsLazyPagingItems()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            showShortToast(context, message)
        }
    }

    LaunchedEffect(uiState.isLogAdded) {
        uiState.isLogAdded?.let { isAdded ->
            if (isAdded) {
                tasklogsList.refresh()
                viewModel.resetUiState()
            }
        }
    }

    TasklogsScreen(
        modifier = modifier.fillMaxSize(),
        tasklogs = tasklogsList,
        taskName = taskName,
        sendTasklog = viewModel::createTasklogs,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasklogsScreen(
    modifier: Modifier = Modifier,
    tasklogs: LazyPagingItems<TasklogsComponentViewData>,
    taskName: String,
    sendTasklog: (message: String) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TasklogsAppBar(modifier = Modifier.fillMaxWidth(), taskName, navigateBack)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(
                    count = tasklogs.itemCount,
                    contentType = tasklogs.itemContentType {
                        "TasklogsComponentViewData"
                    }
                ) { index: Int ->
                    when (val feedItem = tasklogs[index]) {
                        is TasklogsComponentViewData.LogDateViewData -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                LogDateComponent(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    logDateData = feedItem
                                )
                            }
                        }

                        is TasklogsComponentViewData.TasklogViewData -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                TasklogsComponent(
                                    modifier = Modifier.align(Alignment.End),
                                    data = feedItem
                                )
                            }
                        }

                        else -> {

                        }
                    }
                }
            }
            TaskLogInput(
                modifier = Modifier.fillMaxWidth(),
                sendTasklog = sendTasklog
            )
        }
    }
}

@Composable
fun TasklogsAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigateBack: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigateBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                TextH20(
                    text = title
                )
            }
            //Add SearchView Later
        }
    }
}