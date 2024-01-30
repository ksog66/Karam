package com.kklabs.karam.presentation.tasklogs

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.domain.model.TasklogsComponentViewData
import com.kklabs.karam.presentation.components.LogDateComponent
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

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            showShortToast(context, message)
        }
    }

    LaunchedEffect(key1 = uiState.isTaskIdValid) {
        if (!uiState.isTaskIdValid) {
            showShortToast(context, "Taskid Invalid")
            navigateBack()
        }
    }

    TasklogsScreen(modifier = modifier.fillMaxSize(), uiState.feed.asReversed(), taskName = taskName)
}

@Composable
fun TasklogsScreen(
    modifier: Modifier = Modifier,
    feed: List<TasklogsComponentViewData>,
    taskName: String
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TasklogsAppBar(modifier = Modifier.fillMaxWidth(), taskName)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues)
        ) {
            this.items(feed) {feedItem ->
                when (feedItem) {
                    is TasklogsComponentViewData.LogDateViewData -> {
                        LogDateComponent(logDateData = feedItem)
                    }
                    is TasklogsComponentViewData.TasklogViewData -> {
                        TasklogsComponent(data = feedItem)
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