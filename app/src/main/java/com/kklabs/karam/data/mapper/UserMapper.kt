package com.kklabs.karam.data.mapper

import com.kklabs.karam.data.remote.response.UserResponse
import com.kklabs.karam.domain.model.User

fun UserResponse.toUser(): User {
    return User(
        userId,
        username,
        name,
        userDateCreated
    )
}