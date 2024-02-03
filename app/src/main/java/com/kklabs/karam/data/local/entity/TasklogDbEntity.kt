package com.kklabs.karam.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
class TasklogDbEntity (
    @PrimaryKey
    val id: Int,
    val content: String,
    val dateCreated: Date,
    val taskId: Int,
    val type: String,
    val userId: Int,
    val isLastRow:Boolean
)