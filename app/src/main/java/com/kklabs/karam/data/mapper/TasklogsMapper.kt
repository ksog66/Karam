package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.remote.response.TasklogResponse

fun TasklogResponse.toTasklogDbEntity(isLastRow: Boolean): TasklogDbEntity {
    return TasklogDbEntity(
        content = content,
        id = id,
        dateCreated = dateCreated,
        taskId = taskId,
        type = type,
        userId = userId,
        isLastRow = isLastRow
    )
}

fun LogEntity.TasklogEntity.toTasklogDbEntity(): TasklogDbEntity {
    return TasklogDbEntity(
        content = content,
        dateCreated = dateCreated,
        taskId = taskId,
        type = type,
        userId = userId,
        id = id,
        isLastRow = isLastRow
    )
}