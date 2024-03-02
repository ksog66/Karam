package com.kklabs.karam.presentation.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.kklabs.karam.data.ds.ConfigPreferences
import com.kklabs.karam.data.mapper.toUser
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.repo.AuthRepository
import com.kklabs.karam.data.repo.OneTapSignInResponse
import com.kklabs.karam.data.repo.SignInWithGoogleResponse
import com.kklabs.karam.data.repo.UserRepository
import com.kklabs.karam.domain.model.User
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.kklabs.karam.core.Response.*

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    val oneTapClient: SignInClient,
    private val configPreferences: ConfigPreferences
) : BaseViewModel() {

    private val _state =
        MutableStateFlow(SignInState().copy(existingUser = configPreferences.getUserData()))
    val state = _state.asStateFlow()

    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Success(null))
        private set

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
            Log.e(TAG, "Error while creating user", e)
            throw e
        }
    }

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Loading
        oneTapSignInResponse = authRepository.oneTapSignInWithGoogle()
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = launchIO {
        oneTapSignInResponse = Loading
        try {
            val signInWithGoogleResponse = authRepository.firebaseSignInWithGoogle(googleCredential)
            when (signInWithGoogleResponse) {
                is Failure -> {
                    _state.update {
                        it.copy(
                            isSignInSuccessful = false,
                            signInError = signInWithGoogleResponse.e.message,
                            existingUser = null
                        )
                    }
                }
                is Loading -> {

                }
                is Success -> {
                    val user =
                        createUser(
                            signInWithGoogleResponse.data!!.name!!,
                            signInWithGoogleResponse.data.email!!,
                            signInWithGoogleResponse.data.username!!
                        )
                    saveUser(user)
                    _state.update {
                        it.copy(
                            isSignInSuccessful = true,
                            signInError = null
                        )
                    }
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = e.message,
                    existingUser = null
                )
            }
        }
    }

    private suspend fun saveAuthToken(token: String) {
        configPreferences.setAuthToken(token)
    }

    private suspend fun saveUser(user: User) {
        configPreferences.saveUserData(user)
    }

}