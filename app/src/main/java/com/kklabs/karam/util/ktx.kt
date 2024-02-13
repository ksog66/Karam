package com.kklabs.karam.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun showShortToast(context: Context?, text: String?) {
    if (context == null || text.isNullOrEmpty()) return
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun showLongToast(context: Context?,  text: String?) {
    if (context == null || text.isNullOrEmpty()) return
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun formatDate(timestamp: Long): String {
    // Assuming timestamp is in milliseconds, if it's in seconds, multiply by 1000
    val date = Date(timestamp)

    // Specify the desired date format
    val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.US)

    // Format the date and return the result
    return dateFormat.format(date)
}