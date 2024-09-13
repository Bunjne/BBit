package com.bunjne.bbit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bunjne.bbit.extension.initNav
import com.bunjne.bbit.ui.navigation.LoginScreenNav
import com.bunjne.bbit.ui.navigation.WorkspaceListScreenNav
import com.bunjne.bbit.ui.theme.BBitTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    topSafeArea: Float,
    bottomSafeArea: Float,
    viewModel: BBitAppViewModel = koinInject()
) {

    val uiState by viewModel.uiState.collectAsState()

    KoinContext {
        BBitTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .imePadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (uiState.refreshToken.isNullOrEmpty()) {
                    LoginScreenNav().initNav()
                } else {
                    WorkspaceListScreenNav().initNav()
                }
            }
        }
    }
}