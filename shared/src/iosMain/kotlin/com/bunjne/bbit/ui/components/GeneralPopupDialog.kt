package com.bunjne.bbit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import platform.UIKit.UIAlertAction.Companion.actionWithTitle
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertActionStyleDestructive
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert


@Composable
actual fun PlatformPopupDialog(
    title: String,
    message: String,
    positiveText: String,
    negativeText: String?,
    onConfirmed: () -> Unit,
    onDismissed: () -> Unit
) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = title,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )
    if (!negativeText.isNullOrBlank()) {
        alert.addAction(
            actionWithTitle(
                title = negativeText,
                style = UIAlertActionStyleDefault,
                handler = { onDismissed() }
            )
        )
    }
    alert.addAction(
        actionWithTitle(
            title = positiveText,
            style = UIAlertActionStyleDestructive,
            handler = { onConfirmed() }
        )
    )
    LocalUIViewController.current.showViewController(alert, null)
}