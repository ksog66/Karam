package com.kklabs.karam.presentation.auth

import com.kklabs.karam.domain.model.User

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val existingUser: User? = null
)
