package com.bunjne.bbit.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformPopupDialog(
    title: String,
    message: String,
    positiveText: String,
    negativeText: String? = null,
    onConfirmed: () -> Unit,
    onDismissed: () -> Unit = {}
)