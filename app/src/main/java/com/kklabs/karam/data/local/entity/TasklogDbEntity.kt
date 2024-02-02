package com.kklabs.karam.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
class TasklogDbEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String?,
    val dateCreated: Date?,
    val taskId: Int?,
    val type: String?,
    val userId: Int?,
    val logType: String?,
    val date: Long?
)