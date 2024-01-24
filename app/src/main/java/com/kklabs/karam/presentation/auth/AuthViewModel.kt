package com.kklabs.karam.presentation.auth

import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.repo.UserRepository
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    suspend fun createUser(request: CreateUserRequest) = launchIO({

    }) {

    }

}