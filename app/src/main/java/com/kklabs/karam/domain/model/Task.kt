package com.kklabs.karam.domain.model

import com.squareup.moshi.Json
import java.util.Date

data class Task(
    val id: Int,
    val color: String,
    val dateCreated: Date,
    val dateModified: Date?,
    val icon: String,
    val title: String,
    val userId: Int
)