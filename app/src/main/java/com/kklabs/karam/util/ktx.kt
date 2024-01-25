package com.kklabs.karam.util

import android.content.Context
import android.widget.Toast

fun showShortToast(context: Context?, text: String?) {
    if (context == null || text.isNullOrEmpty()) return
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun showLongToast(context: Context?,  text: String?) {
    if (context == null || text.isNullOrEmpty()) return
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}