package com.bunjne.bbit

import androidx.compose.ui.window.ComposeUIViewController
import com.bunjne.bbit.di.KoinInitializer
import com.bunjne.bbit.ui.App
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
        enforceStrictPlistSanityCheck = false
    }
) {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App(
        darkTheme = isDarkTheme,
        dynamicColor = false // iOS does not support for dynamic color feature
    )
}