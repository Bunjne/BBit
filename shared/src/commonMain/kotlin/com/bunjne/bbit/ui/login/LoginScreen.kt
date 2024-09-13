package com.bunjne.bbit.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bunjne.bbit.ui.components.FullScreenLoadingDialog
import com.bunjne.bbit.ui.components.PlatformWebView
import com.bunjne.bbit.ui.components.PlatformWebViewState
import io.github.aakira.napier.Napier

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
    var platformWebViewState by remember {
        mutableStateOf(PlatformWebViewState(null, false))
    }
    val shouldShowLoading by remember {
        derivedStateOf {
            platformWebViewState.isLoading
                    || uiState.isLoading
                    || (platformWebViewState.url?.contains(AUTH_CALLBACK_URL_PREFIX) == true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            uiState.isError -> {
                Text(text = uiState.errorMessage.toString())
            }

            else -> {
                if (uiState.isSuccess) {
                    onLoginSuccess()
                }

                PlatformWebView(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    url = WEB_VIEW_URL,
                    platformWebViewState = platformWebViewState,
                    webViewState = {
                        Napier.d("PlatformWebView webViewState: $it")
                        platformWebViewState = it
                        platformWebViewState.url?.let { url ->
                            if (url.contains(AUTH_CALLBACK_URL_PREFIX, true)) {
                                viewModel.onUIEvent(LoginUiEvent.OnLoginClicked(url.substringAfter("code=")))
                            }
                        }
                    }
                )
            }
        }
    }

    if (shouldShowLoading) {
        FullScreenLoadingDialog()
    }
}