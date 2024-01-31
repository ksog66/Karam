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
import com.kklabs.karam.domain.model.TasklogsComponentViewData

@Composable
fun TasklogsComponent(
    modifier: Modifier = Modifier,
    data: TasklogsComponentViewData.TasklogViewData
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(Color.Green, MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        TextP40(
            text = data.content,
            color = Color.Black
        )
    }
}

@Composable
@Preview
fun TasklogsComponentPreview() {

}
