package com.kklabs.karam.domain.model

data class User(
    val id: Int,
    val username: String,
    val name: String,
    val dateCreated: Long
)