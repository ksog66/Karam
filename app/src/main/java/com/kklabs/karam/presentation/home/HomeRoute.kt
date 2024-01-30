package com.kklabs.karam.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.R
import com.kklabs.karam.data.remote.response.TasksKaram
import com.kklabs.karam.presentation.components.Heatmap
import com.kklabs.karam.presentation.components.TextH10
import com.kklabs.karam.presentation.components.TextH20
import com.kklabs.karam.util.generateYearList

const val FORCE_FETCH_HOME_FEED_KEY = "forceFetchHomeFeedKey"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    forceFetchFeed: Boolean,
    viewModel: HomeViewModel = hiltViewModel(),
    userCreatedYear: Int,
    onNewTaskClick: () -> Unit,
    onLogClick: (name: String, id:Int) -> Unit
) {
    val yearRange = remember {
        generateYearList(userCreatedYear).asReversed()
    }
    var selectedYear by remember { mutableStateOf(yearRange.first()) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var forceFetch by remember { mutableStateOf(forceFetchFeed) }

    var showYearSelectionDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedYear, forceFetch) {
        if (uiState !is HomeFeedUiState.Success || forceFetch) {
            viewModel.getHomeData(selectedYear)
            forceFetch = false
        }
    }

    if (showYearSelectionDialog) {
        Dialog(
            onDismissRequest = { showYearSelectionDialog = false },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent
            ) {
                YearSelection(selectedYear = selectedYear, yearRange = yearRange) {
                    forceFetch = true
                    showYearSelectionDialog = false
                    selectedYear = it
                }
            }
        }

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
                onNewTaskClick = onNewTaskClick,
                selectedYear = selectedYear,
                onLogClick = onLogClick,
                onSelectYearClick = {
                    showYearSelectionDialog = true
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    cumulativeLogcount: Map<Long, Int>,
    tasksKaram: List<TasksKaram>,
    onNewTaskClick: () -> Unit,
    selectedYear: Int,
    onSelectYearClick: () -> Unit,
    onLogClick: (name: String, id:Int) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeAppBar(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
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
                    tasklogs = cumulativeLogcount,
                    selectedYear = selectedYear,
                    onSelectYearClick = onSelectYearClick
                )
            }
            this.items(
                tasksKaram,
                key = { taskItem -> taskItem.id }
            ) { taskItem ->
                TaskDisplay(
                    task = taskItem,
                    type = TaskDisplayType.INDIVIDUAL,
                    tasklogs = taskItem.karam,
                    selectedYear = null,
                    onLogClick = {
                        onLogClick(taskItem.name, taskItem.id)
                    }
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
        TextH10(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(id = R.string.app_name)
        )
    }
}

@Composable
fun YearSelection(
    modifier: Modifier = Modifier,
    selectedYear: Int,
    yearRange: List<Int>,
    onSelectYear: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        this.items(
            yearRange,
            key = { year -> year }
        ) { year ->
            Box(
                modifier = Modifier
                    .clickable { onSelectYear(year) }
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (year == selectedYear) Color.Blue else Color.White)
                    .padding(8.dp)
            ) {
                TextH20(
                    text = year.toString(),
                    color = if (year == selectedYear) Color.White else Color.Black
                )
            }
        }
    }
}