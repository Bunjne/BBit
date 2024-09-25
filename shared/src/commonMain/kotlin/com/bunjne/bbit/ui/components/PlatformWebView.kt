package com.bunjne.bbit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PlatformWebView(modifier: Modifier = Modifier, url: String, platformWebViewState: PlatformWebViewState, onWebViewStateChanged: (PlatformWebViewState) -> Unit)

data class PlatformWebViewState(
    val url: String?,
    val isLoading: Boolean
)