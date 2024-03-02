package com.kklabs.karam.data.repo

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.kklabs.karam.Constants.SIGN_IN_REQUEST
import com.kklabs.karam.Constants.SIGN_UP_REQUEST
import com.kklabs.karam.core.Response
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.kklabs.karam.core.Response.*
import com.kklabs.karam.presentation.auth.UserData

typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias SignInWithGoogleResponse = Response<UserData>

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest
)  {
    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Success(signUpResult)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            Success(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        name = displayName,
                        email = email,
                        profilePictureUrl = photoUrl?.toString()
                    )
                }
            )
        } catch (e: Exception) {
            Failure(e)
        }
    }

}