package com.kklabs.karam.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kklabs.karam.R
import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.domain.model.karamColors
import com.kklabs.karam.presentation.components.ColorsGrid
import com.kklabs.karam.presentation.components.IconsGrid
import com.kklabs.karam.presentation.components.TextH10
import com.kklabs.karam.presentation.components.TextH30
import com.kklabs.karam.presentation.components.TextH40
import com.kklabs.karam.presentation.components.TextP30
import com.kklabs.karam.util.TaskIcons.AllTaskIcons
import com.kklabs.karam.util.showShortToast

@Composable
fun CreateTaskRoute(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
    navigateBack: (forceFetch: Boolean) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is CreateTaskUiState.Error -> {
            val errorMessage = (uiState as CreateTaskUiState.Error).message
            showShortToast(context, errorMessage)
            viewModel.resetState()
        }
        is CreateTaskUiState.Idle -> {

        }
        is CreateTaskUiState.Success -> {
            showShortToast(context, stringResource(id = R.string.task_created_successfully))
            navigateBack(true)
        }
    }
    CreateTaskScreen(
        modifier = modifier,
        addNewTask = viewModel::createTask,
        navigateBack = {
            navigateBack.invoke(false)
        }
    )
}

@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    addNewTask: (request: CreateTaskRequest) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val taskName = rememberSaveable { mutableStateOf("") }
    val selectedIcon = rememberSaveable { mutableStateOf(AllTaskIcons.first()) }
    val selectedColor = rememberSaveable { mutableStateOf(karamColors.first().value) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .background(Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Go Back",
                    tint = Color.White
                )
            }

            TextH10(
                text = stringResource(id = R.string.new_karam)
            )
        }

        OutlinedTextField(
            value = taskName.value,
            onValueChange = {
                taskName.value = it
            },
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp
            ),
            label = { Text("Name") },
            placeholder = { Text("Enter karam name") },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(24.dp))
        TextH40(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.icon_literal)
        )
        IconsGrid(
            modifier = Modifier.fillMaxWidth(),
            selectedIcon = selectedIcon.value,
            onIconSelect = {
                selectedIcon.value = it
            }
        )


        Spacer(modifier = Modifier.height(24.dp))
        TextH40(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.color_literal)
        )
        ColorsGrid(
            modifier = Modifier.fillMaxWidth(),
            selectedColor = selectedColor.value,
            onColorSelect = {
                selectedColor.value = it
            }
        )

        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            onClick = {
                if (taskName.value.isEmpty()) {
                    //Write a toast
                    return@Button
                }
                val request = CreateTaskRequest(
                    title = taskName.value,
                    icon = selectedIcon.value,
                    color = selectedColor.value,
                    dateCreated = System.currentTimeMillis()
                )
                addNewTask(request)
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            TextP30(text = stringResource(id = R.string.add_karam), color = Color.White)
        }
    }

}