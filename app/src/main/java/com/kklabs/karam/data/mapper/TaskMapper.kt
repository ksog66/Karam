package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.remote.response.TaskResponse
import com.kklabs.karam.domain.model.Task


fun TaskResponse.toTask() : Task {
    return Task(
     id, color, dateCreated, dateModified, icon, title, userId
    )
}