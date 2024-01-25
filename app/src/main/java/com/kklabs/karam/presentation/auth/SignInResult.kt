package com.kklabs.karam.presentation.auth

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

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
