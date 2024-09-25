package com.bunjne.bbit.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bunjne.bbit.ui.App
import com.bunjne.bbit.ui.theme.BBitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    BBitTheme(
        darkTheme = true,
        dynamicColor = true
    ) {}
}
