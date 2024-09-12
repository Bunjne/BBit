package com.bunjne.bbit.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bunjne.bbit.domain.usecase.ViewState
import com.bunjne.bbit.ui.components.FullScreenLoadingDialog
import com.bunjne.bbit.ui.components.PlatformWebView
import com.bunjne.bbit.ui.components.PlatformWebViewState

private const val BASE_URL = "https://bitbucket.org/site/oauth2/"
private const val BITBUCKET_CLIENT_ID = "YW5N4Zsq76DyKRrBHj"

private const val WEB_VIEW_URL =
    "${BASE_URL}authorize?client_id=${BITBUCKET_CLIENT_ID}&response_type=code"
private const val AUTH_CALLBACK_URL_PREFIX = "com.seven.bit://authorization"

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var platformWebViewState by remember {
        mutableStateOf(PlatformWebViewState(null, false))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val loginState = uiState.loginState) {
            is ViewState.Success -> {
                onLoginSuccess()
            }

            is ViewState.Loading -> {}

            is ViewState.Error -> {
                println(loginState.toString())
                Text(text = loginState.toString())
            }
        }

        PlatformWebView(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            url = WEB_VIEW_URL,
            platformWebViewState = platformWebViewState,
            loginState = {
                println("PlatformWebView: $it")
                platformWebViewState = it
                platformWebViewState.url?.let { url ->
                    if (url.contains(AUTH_CALLBACK_URL_PREFIX, true)) {
                        viewModel.onUIEvent(LoginUIEvent.OnLoginClicked(url.substringAfter("code=")))
                    }
                }
            }
        )
    }

    if (platformWebViewState.isLoading) {
        FullScreenLoadingDialog()
    }
}