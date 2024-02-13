package com.kklabs.karam.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kklabs.karam.MainViewModel
import com.kklabs.karam.presentation.auth.AuthRoute
import com.kklabs.karam.presentation.auth.GoogleAuthUiClient
import com.kklabs.karam.presentation.home.FORCE_FETCH_HOME_FEED_KEY
import com.kklabs.karam.presentation.home.HomeRoute
import com.kklabs.karam.presentation.navigation.KaramScreens
import com.kklabs.karam.presentation.navigation.TASK_ID_KEY
import com.kklabs.karam.presentation.navigation.TASK_NAME_KEY
import com.kklabs.karam.presentation.tasklogs.TasklogsRoute
import com.kklabs.karam.presentation.tasks.CreateTaskRoute
import com.kklabs.karam.presentation.tasks.EditTaskRoute
import com.kklabs.karam.presentation.welcome.WelcomeRoute
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KaramApp(modifier: Modifier = Modifier, googleAuthUiClient: GoogleAuthUiClient) {

    Surface(
        modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KaramNavHost(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val existingUser by mainViewModel.existingUser.collectAsStateWithLifecycle()

    val userCreatedYear = existingUser?.dateCreated?.let { date ->
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.get(Calendar.YEAR)
    } ?: Calendar.getInstance().get(Calendar.YEAR)

    NavHost(
        navController = navHost,
        startDestination = if (existingUser != null) KaramScreens.Home.route else KaramScreens.LoginScreen.route
    ) {
        composable(route = KaramScreens.Welcome.route) {
            WelcomeRoute(modifier)
        }

        composable(route = KaramScreens.LoginScreen.route) {
            AuthRoute(modifier, googleAuthUiClient) {
                navHost.navigate(
                    route = KaramScreens.Home.route,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(KaramScreens.LoginScreen.route, true).build()
                )
            }
        }

        composable(route = KaramScreens.Home.route) {
            val forceFetchHomeFeed =
                it.savedStateHandle.get<Boolean>(FORCE_FETCH_HOME_FEED_KEY) ?: false
            HomeRoute(
                modifier = modifier,
                userCreatedYear = userCreatedYear,
                onLogClick = { name, id ->
                    navHost.navigate(
                        KaramScreens.Tasklogs.passTaskNameAndId(name, id)
                    )
                },
                forceFetchFeed = forceFetchHomeFeed,
                onNewTaskClick = {
                    navHost.navigate(KaramScreens.CreateTask.route)
                },
                onTaskClick = {
                    navHost.navigate(
                        KaramScreens.EditTask.passTaskId(it)
                    )
                }
            )
        }

        composable(route = KaramScreens.CreateTask.route) {
            CreateTaskRoute(modifier) {
                navHost.previousBackStackEntry?.savedStateHandle?.set<Boolean>(
                    FORCE_FETCH_HOME_FEED_KEY,
                    it
                )
                navHost.navigateUp()
            }
        }

        composable(
            route = KaramScreens.Tasklogs.route,
            arguments = listOf(navArgument(TASK_ID_KEY) { type = NavType.IntType },
                navArgument(TASK_NAME_KEY) { type = NavType.StringType })
        ) {
            val taskName = it.arguments?.getString(TASK_NAME_KEY) ?: ""
            TasklogsRoute(
                modifier = modifier,
                taskName = taskName,
                navigateBack = {
                    navHost.navigateUp()
                }
            )
        }

        composable(
            route = KaramScreens.EditTask.route,
            arguments = listOf(navArgument(TASK_ID_KEY) { type = NavType.IntType })
        ) {
            EditTaskRoute(modifier = modifier) {
                navHost.previousBackStackEntry?.savedStateHandle?.set<Boolean>(
                    FORCE_FETCH_HOME_FEED_KEY,
                    it
                )
                navHost.navigateUp()
            }
        }
    }
}