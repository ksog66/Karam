package com.kklabs.karam.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kklabs.karam.presentation.navigation.KaramScreens

@Composable
fun KaramApp(modifier: Modifier = Modifier) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        Scaffold { paddingValues ->
            KaramNavHost(
                modifier = Modifier.padding(paddingValues),
                navHost = navController
            )
        }
    }
}

@Composable
fun KaramNavHost(
    modifier: Modifier = Modifier,
    navHost: NavHostController
) {
    NavHost(
        navController = navHost,
        startDestination = KaramScreens.Welcome.route
    ) {
        

    }
}