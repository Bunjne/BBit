package com.bunjne.bbit.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
actual fun PlatformPopupDialog(
    title: String,
    message: String,
    positiveText: String,
    negativeText: String?,
    onConfirmed: () -> Unit,
    onDismissed: () -> Unit
) {
    AlertDialog(
        title = { Text(text = title, style = MaterialTheme.typography.titleMedium) },
        text = { Text(text = message, style = MaterialTheme.typography.bodyMedium) },
        onDismissRequest = onDismissed,
        confirmButton = {
            TextButton(onClick = onConfirmed) {
                Text(
                    text = positiveText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            if (!negativeText.isNullOrBlank()) {
                TextButton(onClick = onDismissed) {
                    Text(
                        text = negativeText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
    )
}