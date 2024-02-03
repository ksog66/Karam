package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.domain.model.TasklogViewData

fun TasklogDbEntity.toTasklogViewData(): TasklogViewData {
    return TasklogViewData(
        id = id,
        content = content,
        dateCreated = dateCreated,
        taskId = taskId,
        type = type,
        userId = userId,
        isLastRow = isLastRow
    )
}