package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.domain.model.TasklogsComponentViewData

fun TasklogDbEntity.toTasklogViewData(): TasklogsComponentViewData {
    return try {
        TasklogsComponentViewData.TasklogViewData(
            content = content!!,
            dateCreated = dateCreated!!,
            id = tasklogId!!,
            taskId = taskId!!,
            type = type!!,
            userId = userId!!
        )
    } catch (e: NullPointerException) {
        TasklogsComponentViewData.UnknownViewData
    }
}

fun TasklogDbEntity.toLogDateViewData(): TasklogsComponentViewData {
    return try {
        TasklogsComponentViewData.LogDateViewData(
            date = date!!
        )
    } catch (e: NullPointerException) {
        TasklogsComponentViewData.UnknownViewData
    }
}