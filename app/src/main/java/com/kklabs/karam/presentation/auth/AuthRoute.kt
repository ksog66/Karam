package com.kklabs.karam.presentation.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.kklabs.karam.R
import com.kklabs.karam.presentation.components.TextH10
import com.kklabs.karam.presentation.components.TextP30
import com.kklabs.karam.ui.theme.KaramTheme
import com.kklabs.karam.util.showLongToast
import kotlinx.coroutines.launch

@Composable
fun AuthRoute(
    modifier: Modifier = Modifier,
    googleAuthUiClient: GoogleAuthUiClient,
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if(googleAuthUiClient.getSignedInUser() != null) {
            navigateToHome.invoke()
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            navigateToHome.invoke()
            //showShortToast()
            viewModel.resetState()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == ComponentActivity.RESULT_OK) {
                lifecycleOwner.lifecycleScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    AuthScreen(
        modifier = modifier,
        state
    ) {
        lifecycleOwner.lifecycleScope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
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
        TextH10(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.karam_karo_literal)
        )

        Spacer(modifier = Modifier.width(24.dp))

        TextH10(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(id = R.string.parth_literal)
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