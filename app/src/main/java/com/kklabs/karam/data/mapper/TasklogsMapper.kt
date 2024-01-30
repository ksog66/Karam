package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.remote.response.LogDate
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.domain.model.TasklogsComponentViewData


fun TasklogResponse.toTasklogViewData(): TasklogsComponentViewData.TasklogViewData {
    return TasklogsComponentViewData.TasklogViewData(
        content, dateCreated, id, taskId, type, userId
    )
}

fun LogDate.toLogDateViewData(): TasklogsComponentViewData.LogDateViewData {
    return TasklogsComponentViewData.LogDateViewData(date)
}