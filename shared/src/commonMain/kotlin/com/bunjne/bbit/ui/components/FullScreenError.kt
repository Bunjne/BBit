package com.bunjne.bbit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ErrorPopup(
    modifier: Modifier = Modifier,
    error: String,
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        )
    ) {
        Column(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 450.dp)
                    .verticalScroll(state = rememberScrollState()),
                text = error,
            )
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 20.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    onDismiss()
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "Ok",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}