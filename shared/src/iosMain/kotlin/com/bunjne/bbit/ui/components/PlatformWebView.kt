package com.bunjne.bbit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformWebView(modifier: Modifier, url: String, loginState: (String) -> Unit) {
    val rememberedNavigationDelegate = remember { WKNavigationDelegate(loginState) }
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

class WKNavigationDelegate(private val loginState: (String) -> Unit) : NSObject(),
    WKNavigationDelegateProtocol {
    override fun webView(
        webView: WKWebView,
        decidePolicyForNavigationAction: WKNavigationAction,
        decisionHandler: (WKNavigationActionPolicy) -> Unit,
    ) {
        println("webView, decisionHandler: $decisionHandler")
        loginState(decidePolicyForNavigationAction.request.URL.toString())
        decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
    }
}