package com.bunjne.bbit.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenLoadingDialog() {
    Dialog(
        onDismissRequest = {},
        properties =  DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        )
    ) {
        ProgressLoader()
    }
}