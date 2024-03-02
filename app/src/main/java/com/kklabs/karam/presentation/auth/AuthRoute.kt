package com.kklabs.karam.presentation.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.kklabs.karam.R
import com.kklabs.karam.core.Response
import com.kklabs.karam.presentation.components.Loader
import com.kklabs.karam.presentation.components.TextH1
import com.kklabs.karam.presentation.components.TextH30
import com.kklabs.karam.presentation.components.TextP30
import com.kklabs.karam.ui.theme.KaramTheme
import com.kklabs.karam.util.showLongToast
import com.kklabs.karam.util.showShortToast

@Composable
fun AuthRoute(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                viewModel.signInWithGoogle(googleCredentials)
            } catch (it: ApiException) {
                print(it)
            }
        }
    }

    when (val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Response.Failure -> {
            showShortToast(context, oneTapSignInResponse.e.localizedMessage)
        }
        is Response.Loading -> {
            Loader(
                modifier = Modifier.fillMaxSize(),
                text = "Signing in with google..."
            )
        }
        is Response.Success -> {
            oneTapSignInResponse.data?.let {signInResult ->
                LaunchedEffect(signInResult) {
                    val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
                    launcher.launch(intent)
                }
            }
        }
    }

    LaunchedEffect(key1 = state.existingUser) {
        if (state.existingUser != null) {
            navigateToHome.invoke()
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            navigateToHome.invoke()
            viewModel.resetState()
        }
    }

    AuthScreen(
        modifier = modifier,
        state
    ) {
        viewModel.oneTapSignIn()
    }
}

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            showLongToast(context, error)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        TextH1(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            text = "Karam"
        )

        Spacer(modifier = Modifier.height(120.dp))

        TextH30(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.karam_shlok),
            fontStyle = FontStyle.Italic
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onSignInClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "Google Button",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextP30(text = stringResource(id = R.string.sign_up_with_google))
            }
        }

    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    KaramTheme {
        AuthScreen(state = SignInState()) {

        }
    }
}