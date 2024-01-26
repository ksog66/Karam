package com.kklabs.karam.domain.model

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val username: String,
    val name: String,
    val dateCreated: Date
)