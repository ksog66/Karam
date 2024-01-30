package com.kklabs.karam.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kklabs.karam.data.remote.response.TasklogResponse
import java.util.Date

@Composable
fun TasklogsComponent(data: TasklogResponse) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.Green, MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Text(
            text = data.content,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
@Preview
fun TasklogsComponentPreview() {
    val mockTaskLogData = TasklogResponse(
        "bench press 60kg",
        Date(),
        5,
        3,
        "TEXT_MESSAGE",
        1
    )
    TasklogsComponent(data = mockTaskLogData)
}
