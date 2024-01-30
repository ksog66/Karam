package com.kklabs.karam.presentation.tasklogs

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.presentation.components.TextH20

@Composable
fun TasklogsRoute(
    modifier: Modifier = Modifier,
    taskId: Int,
    taskName: String,
    viewModel: TasklogsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TasklogsScreen(modifier = modifier.fillMaxSize(), taskName = taskName)
}

@Composable
fun TasklogsScreen(
    modifier: Modifier = Modifier,
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