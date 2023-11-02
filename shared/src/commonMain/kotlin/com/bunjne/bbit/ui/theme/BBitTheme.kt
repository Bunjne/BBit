package com.bunjne.bbit.ui.theme

import androidx.compose.runtime.Composable

@Composable
expect fun BBitTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)