package com.bunjne.bbit.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bunjne.bbit.domain.usecase.ViewState
import com.bunjne.bbit.ui.components.ProgressLoader
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

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

    val webViewState = rememberWebViewState(WEB_VIEW_URL)

    val loginCode by remember(webViewState.lastLoadedUrl) {
        mutableStateOf(webViewState.lastLoadedUrl.toString().substringAfter("code="))
    }

    LaunchedEffect(webViewState.lastLoadedUrl) {
        if (webViewState.lastLoadedUrl.toString().contains(AUTH_CALLBACK_URL_PREFIX, true))
            viewModel.onUIEvent(LoginUIEvent.OnLoginClicked(loginCode))
    }

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

    webViewState.webSettings.apply {
        customUserAgentString = "random"
        isJavaScriptEnabled = true
        androidWebSettings.apply {
            safeBrowsingEnabled = true
        }
    }

    Box(Modifier.fillMaxSize()) {
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize()
        )

        when (webViewState.loadingState) {
            is LoadingState.Loading -> {
                ProgressLoader()
            }

            is LoadingState.Finished -> {
//                if (webViewState.lastLoadedUrl.toString().contains(AUTH_CALLBACK_URL_PREFIX, true)) {
//                    viewModel.onUIEvent(LoginUIEvent.OnLoginClicked(loginCode))
//                }
            }

            else -> {
                // Do nothing
            }
        }
    }
}