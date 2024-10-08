package com.bunjne.bbit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformWebView(
    modifier: Modifier,
    url: String,
    platformWebViewState: PlatformWebViewState,
    onWebViewStateChanged: (PlatformWebViewState) -> Unit
) {
    val rememberedNavigationDelegate = remember {
        WKNavigationDelegate(
            platformWebViewState = platformWebViewState,
            onWebViewStateChanged = onWebViewStateChanged
        )
    }
    val webConfiguration = WKWebViewConfiguration()
    webConfiguration.applicationNameForUserAgent = "Version/8.0.2 Safari/600.2.5"

    UIKitView(
        modifier = modifier,
        factory = {
            WKWebView(
                frame = CGRectZero.readValue(),
                configuration = webConfiguration
            ).apply {
                navigationDelegate = rememberedNavigationDelegate
                loadRequest(
                    request = NSURLRequest(
                        uRL = NSURL(string = url),
                    )
                )
            }
        }
    )
}

class WKNavigationDelegate(
    private val platformWebViewState: PlatformWebViewState,
    private val onWebViewStateChanged: (PlatformWebViewState) -> Unit
) : NSObject(),
    WKNavigationDelegateProtocol {
    override fun webView(
        webView: WKWebView,
        decidePolicyForNavigationAction: WKNavigationAction,
        decisionHandler: (WKNavigationActionPolicy) -> Unit,
    ) {
        onWebViewStateChanged(
            platformWebViewState.copy(
                url = decidePolicyForNavigationAction.request.URL.toString(),
                isLoading = webView.loading
            )
        )
        decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
    }

    override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
        onWebViewStateChanged(
            platformWebViewState.copy(
                isLoading = false
            )
        )
    }

    override fun webView(webView: WKWebView, didFailNavigation: WKNavigation?, withError: NSError) {
        onWebViewStateChanged(
            platformWebViewState.copy(
                isLoading = false
            )
        )
    }
}