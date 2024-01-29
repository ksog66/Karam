package com.kklabs.karam.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kklabs.karam.R
import com.kklabs.karam.domain.model.karamColors
import com.kklabs.karam.presentation.components.ColorsGrid
import com.kklabs.karam.presentation.components.IconsGrid
import com.kklabs.karam.presentation.components.TextH30
import com.kklabs.karam.presentation.components.TextH40
import com.kklabs.karam.util.TaskIcons.AllTaskIcons

@Composable
fun CreateTaskRoute(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    CreateTaskScreen(
        modifier = modifier,
        navigateBack = navigateBack
    )
}

@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val taskName = rememberSaveable { mutableStateOf("") }
    val selectedIcon = rememberSaveable { mutableStateOf(AllTaskIcons.first()) }
    val selectedColor = rememberSaveable { mutableStateOf(karamColors.first().value) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
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

            TextH30(
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


        TextH40(text = stringResource(id = R.string.icon_literal))
        Spacer(modifier = Modifier.height(4.dp))
        IconsGrid(
            modifier = Modifier.fillMaxWidth(),
            selectedIcon = selectedIcon.value,
            onIconSelect = {
                selectedIcon.value = it
            }
        )

        TextH40(text = stringResource(id = R.string.color_literal))
        Spacer(modifier = Modifier.height(4.dp))
        ColorsGrid(
            modifier = Modifier.fillMaxWidth(),
            selectedColor = selectedColor.value,
            onColorSelect = {
                selectedColor.value = it
            }
        )
    }

}