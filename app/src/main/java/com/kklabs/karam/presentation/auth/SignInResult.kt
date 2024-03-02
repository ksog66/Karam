package com.kklabs.karam.presentation.auth


data class UserData(
    val userId: String,
    val name: String?,
    val username: String?,
    val email: String?,
    val profilePictureUrl: String?
) {
    fun isDetailNull(): Boolean {
        return username == null && name == null && email == null
    }
}
