package com.kklabs.karam.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kklabs.karam.data.remote.response.LogDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LogDateComponent(modifier: Modifier = Modifier, logDateData: LogDate) {
    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(logDateData.date)

    Box(
        modifier = modifier
            .padding(8.dp)
            .background(Color.Gray, MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        TextP30(
            text = formattedDate,
            color = Color.Black
        )
    }
}

@Composable
@Preview
fun LogDateComponentPreview() {
    val mockLogDateData = LogDate(Date().time)
    LogDateComponent(logDateData = mockLogDateData)
}