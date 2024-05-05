package com.bunjne.bbit.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bunjne.bbit.domain.usecase.ViewState
import com.bunjne.bbit.ui.components.PlatformWebView
import com.bunjne.bbit.ui.components.ProgressLoader

private const val BASE_URL = "https://bitbucket.org/site/oauth2/"
private const val BITBUCKET_CLIENT_ID = "***REMOVED***"
private const val WEB_VIEW_URL =
    "${BASE_URL}authorize?client_id=${BITBUCKET_CLIENT_ID}&response_type=code"
private const val AUTH_CALLBACK_URL_PREFIX = "***REMOVED***"

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val loginState = uiState.loginState) {
        is ViewState.Success -> {
            onLoginSuccess()
        }

        is ViewState.Loading -> {
            ProgressLoader()
        }

        is ViewState.Error -> {
            Text(text = loginState.toString())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .statusBarsPadding()
    ) {
        PlatformWebView(
            modifier = Modifier.fillMaxSize(),
            url = WEB_VIEW_URL,
            loginState = {
                println("PlatformWebView: $it")
                if (it.contains(AUTH_CALLBACK_URL_PREFIX, true)) {
                    viewModel.onUIEvent(LoginUIEvent.OnLoginClicked(it.substringAfter("code=")))
                }
            }
        )
    }
}