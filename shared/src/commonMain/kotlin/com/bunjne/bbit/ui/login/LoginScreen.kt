package com.bunjne.bbit.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bunjne.bbit.data.remote.ApiConstants.BITBUCKET_AUTH_CALLBACK_URL
import com.bunjne.bbit.data.remote.ApiConstants.BITBUCKET_CLIENT_ID
import com.bunjne.bbit.data.remote.ApiEndpoints.AUTH_BASE_URL
import com.bunjne.bbit.ui.components.ErrorPopup
import com.bunjne.bbit.ui.components.FullScreenLoadingDialog
import com.bunjne.bbit.ui.components.PlatformWebView
import com.bunjne.bbit.ui.components.PlatformWebViewState
import io.github.aakira.napier.Napier

private val WEB_VIEW_URL =
    "${AUTH_BASE_URL}authorize?client_id=${BITBUCKET_CLIENT_ID}&response_type=code"

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LoginScreen(
        uiState = uiState,
        onLoginSuccess = onLoginSuccess,
        onLoginClicked = { url ->
            viewModel.onUIEvent(LoginUiEvent.OnLoginClicked(url.substringAfter("code=")))
        }
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onLoginSuccess: () -> Unit,
    onLoginClicked: (String) -> Unit
) {
    var platformWebViewState by remember {
        mutableStateOf(PlatformWebViewState(null, true))
    }
    val shouldShowLoading by remember {
        derivedStateOf {
            platformWebViewState.isLoading
                    || uiState.isLoading
                    || (platformWebViewState.url?.contains(BITBUCKET_AUTH_CALLBACK_URL) == true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.isSuccess) {
            onLoginSuccess()
        }

        PlatformWebView(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            url = WEB_VIEW_URL,
            platformWebViewState = platformWebViewState,
            onWebViewStateChanged = {
                Napier.d("PlatformWebView webViewState: $it")
                platformWebViewState = it
                platformWebViewState.url?.let { url ->
                    if (url.contains(BITBUCKET_AUTH_CALLBACK_URL, true)) {
                        onLoginClicked(url.substringAfter("code="))
                    }
                }
            }
        )

        if (uiState.isError) {
            ErrorPopup(
                modifier = Modifier.wrapContentSize(),
                error = uiState.error?.asString().orEmpty(),
            )
        }
    }

    if (shouldShowLoading) {
        FullScreenLoadingDialog()
    }
}