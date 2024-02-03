package com.kklabs.karam.domain.model

import com.squareup.moshi.Json
import java.util.Date

data class TasklogViewData(
    val content: String,
    val dateCreated: Date,
    val id: Int,
    val taskId: Int,
    val type: String,
    val userId: Int,
    val isLastRow: Boolean
)



