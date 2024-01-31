package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.remote.response.LogEntity
import com.kklabs.karam.domain.model.TasklogsComponentViewData


fun LogEntity.TasklogEntity.toTasklogViewData(): TasklogsComponentViewData.TasklogViewData {
    return TasklogsComponentViewData.TasklogViewData(
        content, dateCreated, id, taskId, type, userId
    )
}

fun LogEntity.LogDateEntity.toLogDateViewData(): TasklogsComponentViewData.LogDateViewData {
    return TasklogsComponentViewData.LogDateViewData(date)
}