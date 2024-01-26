package com.kklabs.karam.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    }

}