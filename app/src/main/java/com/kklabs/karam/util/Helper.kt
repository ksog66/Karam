package com.kklabs.karam.util

import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import com.kklabs.karam.R
import com.kklabs.karam.util.TaskIcons.ALARM
import com.kklabs.karam.util.TaskIcons.BED
import com.kklabs.karam.util.TaskIcons.BOOK
import com.kklabs.karam.util.TaskIcons.CAMERA
import com.kklabs.karam.util.TaskIcons.CODE
import com.kklabs.karam.util.TaskIcons.COFFEE
import com.kklabs.karam.util.TaskIcons.CONTROLLER
import com.kklabs.karam.util.TaskIcons.CYCLE
import com.kklabs.karam.util.TaskIcons.FITNESS
import com.kklabs.karam.util.TaskIcons.GROCERY
import com.kklabs.karam.util.TaskIcons.HEART
import com.kklabs.karam.util.TaskIcons.MAN
import com.kklabs.karam.util.TaskIcons.MUSIC
import com.kklabs.karam.util.TaskIcons.PAINT
import com.kklabs.karam.util.TaskIcons.RUPPEE
import com.kklabs.karam.util.TaskIcons.SAVINGS
import com.kklabs.karam.util.TaskIcons.SHOWER
import com.kklabs.karam.util.TaskIcons.SOCCER
import com.kklabs.karam.util.TaskIcons.STUDY
import com.kklabs.karam.util.TaskIcons.WOMAN

fun generateCommitColor(commitCount: Int, baseColor: String): Int {
    if (commitCount == 0) {
        return Color.LTGRAY
    }

    val baseColorInt = Color.parseColor(baseColor)

    return when (commitCount) {
        in 1..2 -> ColorUtils.blendARGB(baseColorInt, Color.WHITE, 0.2f) // Very light shade
        in 3..4 -> ColorUtils.blendARGB(baseColorInt, Color.WHITE, 0.4f) // Light shade
        in 5..6 -> ColorUtils.blendARGB(baseColorInt, Color.WHITE, 0.6f) // Less light shade
        else -> baseColorInt
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateYearList(startYear: Int): List<Int> {
    val currentYear = java.time.Year.now().value
    return if (startYear == currentYear) {
        listOf(startYear)
    } else {
        (startYear..currentYear).toList()
    }
}

@DrawableRes
fun fetchIcon(icon: String?): Int {
    return when (icon) {
        ALARM -> R.drawable.alarm_icon
        BED -> R.drawable.bed_icon
        BOOK -> R.drawable.book_icon
        CAMERA -> R.drawable.camera_icon
        CODE -> R.drawable.code_icon
        COFFEE -> R.drawable.coffee_icon
        CONTROLLER -> R.drawable.controller_icon
        CYCLE -> R.drawable.cycle_icon
        FITNESS -> R.drawable.fitness_icon
        GROCERY -> R.drawable.grocery_icon
        HEART -> R.drawable.heart_icon
        MAN -> R.drawable.man_icon
        MUSIC -> R.drawable.music_icon
        PAINT -> R.drawable.paint_icon
        RUPPEE -> R.drawable.ruppee_icon
        SAVINGS -> R.drawable.savings_icon
        SHOWER -> R.drawable.shower_icon
        SOCCER -> R.drawable.soccer_icon
        STUDY -> R.drawable.study_icon
        WOMAN -> R.drawable.woman_icon
        else -> R.drawable.ic_yoga
    }
}
