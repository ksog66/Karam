package com.kklabs.karam.presentation.navigation

sealed class KaramScreens(val route: String) {

    object Welcome : KaramScreens(route = "welcome_screen")

    object LoginScreen : KaramScreens(route = "login_screen")

    object Home : KaramScreens(route = "home_screen")

    object CreateTask : KaramScreens(route = "create_task")

    object Tasklogs : KaramScreens(route = "task_logs")
}