package com.kklabs.karam.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ComponentCircle(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .size(100.dp)
            .shimmerLoadingAnimation(
                isLoadingCompleted = isLoadingCompleted,
                isLightModeActive = isLightModeActive,
            )
    )
}

@Composable
fun ComponentSquare(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.LightGray)
            .size(100.dp)
            .shimmerLoadingAnimation(
                isLoadingCompleted = isLoadingCompleted,
                isLightModeActive = isLightModeActive,
            )
    )
}

@Composable
fun ComponentRectangle(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = Color.LightGray)
            .height(200.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimation(
                isLoadingCompleted = isLoadingCompleted,
                isLightModeActive = isLightModeActive,
            )
    )
}

@Composable
fun ComponentRectangleLineLong(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 200.dp)
            .shimmerLoadingAnimation(
                isLoadingCompleted = isLoadingCompleted,
                isLightModeActive = isLightModeActive,
            )
    )
}

@Composable
fun ComponentRectangleLineShort(
    isLoadingCompleted: Boolean,
    isLightModeActive: Boolean,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 100.dp)
            .shimmerLoadingAnimation(
                isLoadingCompleted = isLoadingCompleted,
                isLightModeActive = isLightModeActive,
            )
    )
}

@Composable
fun ContentLoadingButton(
    modifier: Modifier,
    onClick: () -> Unit,
    isLoadingCompleted: Boolean,
    isLightMode: Boolean,
) {
    var isStartMode by remember { mutableStateOf(true) }

    Button(
        modifier = modifier,
        onClick = {
            isStartMode = !isStartMode
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor =
            if (isLightMode) {
                if (isStartMode) Color.Blue
                else Color.Red
            } else {
                if (isStartMode) Color.Green
                else Color.Red
            }
        )
    ) {
        Text(
            text = if (isLoadingCompleted) {
                "Start Shimmer Loading Animation ▶\uFE0F"
            } else {
                "Stop Shimmer Loading Animation ⏹\uFE0F"
            },
            color = if (isLightMode) Color.White else {
                if (isStartMode) Color.Black
                else Color.White
            }
        )
    }
}

@Composable
fun ContentModeButton(
    modifier: Modifier,
    onClick: () -> Unit,
    isLightMode: Boolean,
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLightMode) Color.Blue else Color.Green
        )
    ) {
        Text(
            text = if (isLightMode) {
                "Display Dark Mode \uD83C\uDF19"
            } else {
                "Display Light Mode ☀\uFE0F"
            },
            color = if (isLightMode) Color.White else Color.Black
        )
    }
}