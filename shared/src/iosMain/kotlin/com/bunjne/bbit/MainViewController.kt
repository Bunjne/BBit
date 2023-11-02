package com.bunjne.bbit

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController(topSafeArea: Float, bottomSafeArea: Float) = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App(
        darkTheme = isDarkTheme,
        dynamicColor = false, // iOS does not support for dynamic color feature
        topSafeArea = topSafeArea,
        bottomSafeArea = bottomSafeArea
    )
}