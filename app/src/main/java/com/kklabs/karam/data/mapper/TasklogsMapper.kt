package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.domain.model.TasklogsComponentViewData


fun LogEntity.TasklogEntity.toTasklogViewData(): TasklogsComponentViewData.TasklogViewData {
    return TasklogsComponentViewData.TasklogViewData(
        content, dateCreated, id, taskId, type, userId
    )
}

fun LogEntity.LogDateEntity.toLogDateViewData(): TasklogsComponentViewData.LogDateViewData {
    return TasklogsComponentViewData.LogDateViewData(date)
}

fun TasklogResponse.toTasklogViewData(): TasklogsComponentViewData.TasklogViewData {
    return TasklogsComponentViewData.TasklogViewData(
        content, dateCreated, id, taskId, type, userId
    )
}

fun LogEntity.TasklogEntity.toTasklogDbEntity(): TasklogDbEntity {
    return TasklogDbEntity(
        content = content,
        dateCreated = dateCreated,
        taskId = taskId,
        type = type,
        userId = userId,
        logType = logType,
        date = null,
        tasklogId = id,
        entityDateCreated = System.currentTimeMillis()
    )
}

fun LogEntity.LogDateEntity.toTasklogDbEntity(taskId: Int): TasklogDbEntity {
    return TasklogDbEntity(
        content = null,
        dateCreated = null,
        taskId = taskId,
        type = null,
        userId = null,
        logType = logType,
        date = date,
        tasklogId = null,
        entityDateCreated = System.currentTimeMillis()
    )
}