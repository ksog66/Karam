package com.kklabs.karam.presentation.tasklogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kklabs.karam.domain.model.TasklogsComponentViewData
import com.kklabs.karam.presentation.components.LogDateComponent
import com.kklabs.karam.presentation.components.TasklogsComponent
import com.kklabs.karam.presentation.components.TextH20

@Composable
fun TasklogsRoute(
    modifier: Modifier = Modifier,
    taskName: String,
    navigateBack: () -> Unit,
    viewModel: TasklogsViewModel = hiltViewModel()
) {
    val tasklogsList = viewModel.tasklogsPager.collectAsLazyPagingItems()

    TasklogsScreen(
        modifier = modifier.fillMaxSize(),
        tasklogs = tasklogsList,
        taskName = taskName
    )
}

@Composable
fun TasklogsScreen(
    modifier: Modifier = Modifier,
    tasklogs: LazyPagingItems<TasklogsComponentViewData>,
    taskName: String
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TasklogsAppBar(modifier = Modifier.fillMaxWidth(), taskName)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues),
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
                        Column {
                            LogDateComponent(
                                modifier = Modifier.align(Alignment.End),
                                logDateData = feedItem
                            )
                        }
                    }

                    is TasklogsComponentViewData.TasklogViewData -> {
                        Column {
                            TasklogsComponent(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                data = feedItem
                            )
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }
}

@Composable
fun TasklogsAppBar(
    modifier: Modifier = Modifier,
    title: String
) {
    Surface(
        modifier = modifier
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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
                TextH20(
                    text = title
                )
            }
            //Add SearchView Later
        }
    }
}