package com.kklabs.karam.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date


@JsonClass(generateAdapter = true)
data class TasklogResponse(
    @Json(name = "content")
    val content: String,
    @Json(name = "date_created")
    val dateCreated: Date,
    @Json(name = "id")
    val id: Int,
    @Json(name = "task_id")
    val taskId: Int,
    @Json(name = "type")
    val type: String,
    @Json(name = "user_id")
    val userId: Int,
)


sealed class LogEntity(@Json(name = "log_type") logtype: String) {
    @JsonClass(generateAdapter = true)
    data class TasklogEntity(
        @Json(name = "content")
        val content: String,
        @Json(name = "date_created")
        val dateCreated: Date,
        @Json(name = "id")
        val id: Int,
        @Json(name = "task_id")
        val taskId: Int,
        @Json(name = "type")
        val type: String,
        @Json(name = "user_id")
        val userId: Int,
        @Json(name = "log_type")
        val logType: String
    ) : LogEntity(logType)

    @JsonClass(generateAdapter = true)
    data class LogDateEntity(
        val date: Long,
        @Json(name = "log_type")
        val logType: String
    ) : LogEntity(logType)
}

enum class LogType(val mName: String) {
    TASK_LOG("task_log"),
    LOG_DATE("log_date")
}