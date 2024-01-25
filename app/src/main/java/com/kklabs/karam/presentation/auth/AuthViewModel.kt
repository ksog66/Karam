package com.kklabs.karam.presentation.auth

import com.kklabs.karam.data.ds.ConfigPreferences
import com.kklabs.karam.data.mapper.toUser
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.repo.UserRepository
import com.kklabs.karam.domain.model.User
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val configPreferences: ConfigPreferences
) : BaseViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) = launchIO {
        try {
            val isSignInSuccessful = result.data != null
            if (isSignInSuccessful && !result.data!!.isDetailNull()) {
                val user =
                    createUser(result.data.name!!, result.data.email!!, result.data.username!!)
                saveUser(user)
            }
            _state.update {
                it.copy(
                    isSignInSuccessful = isSignInSuccessful,
                    signInError = result.errorMessage
                )
            }
        } catch (e: Exception) {

        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    private suspend fun createUser(name: String, email: String, username: String): User {
        try {
            return when (val res = userRepository.createUser(
                CreateUserRequest(
                    name = name,
                    email = email,
                    username = username
                )
            )) {
                is NetworkResponse.Error -> {
                    throw Error(res.body.message)
                }

                is NetworkResponse.Success -> {
                    saveAuthToken(res.successBody.jwtToken)
                    res.successBody.toUser()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun saveAuthToken(token: String) {
        configPreferences.setAuthToken(token)
    }
    private suspend fun saveUser(user: User) {
        configPreferences.saveUserData(user)
    }

}