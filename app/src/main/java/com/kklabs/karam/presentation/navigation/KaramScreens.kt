package com.kklabs.karam.presentation.navigation

const val TASK_ID_KEY = "task_id"
const val TASK_NAME_KEY = "task_name"

sealed class KaramScreens(val route: String) {

    object Welcome : KaramScreens(route = "welcome_screen")

    object LoginScreen : KaramScreens(route = "login_screen")

    object Home : KaramScreens(route = "home_screen")

    object CreateTask : KaramScreens(route = "create_task")

    object Tasklogs :
        KaramScreens(route = "task_logs?task_id={$TASK_ID_KEY}&task_name={$TASK_NAME_KEY}") {
        fun passTaskNameAndId(
            name: String,
            id: Int
        ): String {
            return "task_logs?task_id=$id&task_name=$name"
        }
    }

    object EditTask : KaramScreens(route = "edit_task?task_id={$TASK_ID_KEY}") {
        fun passTaskId(
            id: Int
        ): String {
            return "edit_task?task_id=$id"
        }
    }
}