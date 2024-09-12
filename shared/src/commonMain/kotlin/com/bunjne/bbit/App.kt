package com.bunjne.bbit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunjne.bbit.extension.initNav
import com.bunjne.bbit.ui.navigation.WorkspaceListScreenNav
import com.bunjne.bbit.ui.theme.BBitTheme
import org.koin.compose.KoinContext

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    topSafeArea: Float,
    bottomSafeArea: Float
) {
    KoinContext {
        BBitTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .background(MaterialTheme.colorScheme.background)
                    .imePadding(),
            ) {

            }
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .imePadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                WorkspaceListScreenNav().initNav()
//                LoginScreenNav().initNav()
//                LaunchesScreenNav().initNav()
            }
        }
    }
}