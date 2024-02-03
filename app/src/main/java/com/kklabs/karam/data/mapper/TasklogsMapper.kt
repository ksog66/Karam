package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.domain.model.TasklogViewData


fun LogEntity.TasklogEntity.toTasklogViewData(): TasklogViewData {
    return TasklogViewData(
        content, dateCreated, id, taskId, type, userId, isLastRow
    )
}

fun TasklogResponse.toTasklogViewData(): TasklogViewData {
    return TasklogViewData(
        content, dateCreated, id, taskId, type, userId, isLastRow = false
    )
}

fun TasklogResponse.toTasklogDbEntity(): TasklogDbEntity {
    return TasklogDbEntity(
        content = content,
        id = id,
        dateCreated = dateCreated,
        taskId = taskId,
        type = type,
        userId = userId,
        isLastRow = false //TODO("write some logic for this to determine is this last row or not)
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