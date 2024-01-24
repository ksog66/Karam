package com.kklabs.karam.util.coroutine

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
    //Sentry exception reporting
    Log.e("EXCEPTION", "", exception)
}

/**
 * This dispatcher is optimized to perform disk or network I/O outside of the main thread.
 * Examples include using the database component, reading from or writing to files, and running any network operations.
 */
fun CoroutineScope.launchIO(
    exceptionHandler: (suspend (Throwable) -> Unit)? = null,
    block: suspend (CoroutineScope) -> Unit
): Job {
    val handler = CoroutineExceptionHandler { ctx, throwable ->
        if (exceptionHandler != null) {
            launch {
                exceptionHandler(throwable)
            }
        } else {
            throw throwable
        }
    }
    return launch(Dispatchers.IO + handler) {
        block(this)
    }
}

/**
 * This dispatcher is optimized to perform CPU-intensive work outside of the main thread.
 * Example use cases include sorting a list and parsing JSON.
 */
fun CoroutineScope.launchDefault(
    exceptionHandler: (suspend (Throwable) -> Unit)? = null,
    block: suspend (CoroutineScope) -> Unit
): Job {
    val handler = CoroutineExceptionHandler { _, throwable ->
        if (exceptionHandler != null) {
            launch {
                exceptionHandler(throwable)
            }
        } else {
            throw throwable
        }
    }
    return launch(Dispatchers.Default + handler) {
        block(this)
    }
}

/**
 * Use this dispatcher to run a coroutine on the main Android thread. This should be used only for interacting with the UI and performing quick work.
 * Examples include calling suspend functions, running Android UI framework operations, and updating LiveData objects.
 */
fun CoroutineScope.launchMain(
    exceptionHandler: (suspend (Throwable) -> Unit)? = null,
    block: suspend (CoroutineScope) -> Unit
): Job {
    val handler = CoroutineExceptionHandler { _, throwable ->
        if (exceptionHandler != null) {
            launch {
                exceptionHandler(throwable)
            }
        } else {
            throw throwable
        }
    }
    return launch(Dispatchers.Main + handler) {
        block(this)
    }
}