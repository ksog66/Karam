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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kklabs.karam.presentation.auth.AuthRoute
import com.kklabs.karam.presentation.auth.GoogleAuthUiClient
import com.kklabs.karam.presentation.home.HomeRoute
import com.kklabs.karam.presentation.navigation.KaramScreens
import com.kklabs.karam.presentation.tasklogs.TasklogsRoute
import com.kklabs.karam.presentation.tasks.CreateTaskRoute
import com.kklabs.karam.presentation.welcome.WelcomeRoute

@Composable
fun KaramApp(modifier: Modifier = Modifier, googleAuthUiClient: GoogleAuthUiClient) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        Scaffold { paddingValues ->
            KaramNavHost(
                modifier = Modifier.padding(paddingValues),
                navHost = navController,
                googleAuthUiClient = googleAuthUiClient
            )
        }
    }
}

@Composable
fun KaramNavHost(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    NavHost(
        navController = navHost,
        startDestination = KaramScreens.LoginScreen.route
    ) {
        composable(route = KaramScreens.Welcome.route) {
            WelcomeRoute(modifier)
        }

        composable(route = KaramScreens.LoginScreen.route) {
            AuthRoute(modifier, googleAuthUiClient) {
                navHost.navigate(KaramScreens.Home.route)
            }
        }

        composable(route = KaramScreens.Home.route) {
            HomeRoute(modifier)
        }

        composable(route = KaramScreens.CreateTask.route) {
            CreateTaskRoute(modifier)
        }

        composable(route = KaramScreens.Tasklogs.route) {
            TasklogsRoute(modifier)
        }
    }
}