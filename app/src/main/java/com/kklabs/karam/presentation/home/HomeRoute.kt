package com.kklabs.karam.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kklabs.karam.presentation.components.Heatmap
import com.kklabs.karam.presentation.components.TextH10

@Composable
fun HomeRoute(modifier: Modifier = Modifier) {
    HomeScreen(modifier)
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Column {
        TextH10(
            text = "Welcome to Home"
        )
        val testData = mutableMapOf<Long, Int>().apply {
            for (i in 1..365) {
                put(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000, (1..10).random())
            }
        }
        Heatmap(modifier = Modifier.fillMaxWidth(), data = testData, baseColor = "#FFFFFF")
    }

}