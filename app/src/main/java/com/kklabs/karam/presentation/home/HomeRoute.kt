package com.kklabs.karam.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.R
import com.kklabs.karam.data.remote.response.TasksKaram
import com.kklabs.karam.presentation.components.Heatmap
import com.kklabs.karam.presentation.components.TextH10

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNewTaskClick: () -> Unit
) {
    val selectedYear = mutableStateOf("2023")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(selectedYear.value) {
        viewModel.getHomeData(selectedYear.value)
    }

    when (uiState) {
        is HomeFeedUiState.Error -> {

        }

        is HomeFeedUiState.Loading -> {

        }

        is HomeFeedUiState.Success -> {
            val data = (uiState as HomeFeedUiState.Success).feed
            HomeScreen(
                modifier = modifier,
                cumulativeLogcount = data.cumulativeKaram,
                tasksKaram = data.tasksKaram,
                onNewTaskClick = onNewTaskClick
            )
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    cumulativeLogcount: Map<Long, Int>,
    tasksKaram: List<TasksKaram>,
    onNewTaskClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeAppBar(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewTaskClick,
                containerColor = Color.Black
            ) {
                Icon(imageVector = Icons.Filled.Add, tint = Color.White, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 74.dp, top = 12.dp),
        ) {
            item {
                TaskDisplay(
                    task = null,
                    type = TaskDisplayType.CUMULATIVE,
                    tasklogs = cumulativeLogcount
                )
            }
            this.items(
                tasksKaram,
                key = { taskItem -> taskItem.id }
            ) { taskItem ->
                TaskDisplay(
                    task = taskItem,
                    type = TaskDisplayType.INDIVIDUAL,
                    tasklogs = taskItem.karam
                )
            }
        }
    }
}

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Color.White
    ) {
        TextH10(text = stringResource(id = R.string.app_name))
    }
}