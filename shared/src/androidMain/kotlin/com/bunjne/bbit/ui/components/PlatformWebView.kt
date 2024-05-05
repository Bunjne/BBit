package com.bunjne.bbit.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
actual fun PlatformWebView(modifier: Modifier, url: String, loginState: (String) -> Unit) {
    val webViewState = rememberWebViewState(url).apply {
        webSettings.apply {
            customUserAgentString = "random"
            isJavaScriptEnabled = true
            androidWebSettings.apply {
                safeBrowsingEnabled = true
            }
        }
    }

    LaunchedEffect(webViewState.lastLoadedUrl) {
        loginState(webViewState.lastLoadedUrl.orEmpty())
    }

    WebView(
        state = webViewState,
        modifier = Modifier.fillMaxSize()
    ) {
        when (webViewState.loadingState) {
            is LoadingState.Loading -> {

            }

            is LoadingState.Finished -> {
                // Do nothing
            }

            else -> {
                // Do nothing
            }
        }
    }
}