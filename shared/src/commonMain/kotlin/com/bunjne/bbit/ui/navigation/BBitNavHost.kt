package com.bunjne.bbit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bunjne.bbit.ui.BBitAppState

@Composable
fun BBitNavHost(
    modifier: Modifier = Modifier,
    appState: BBitAppState,
    startDestination: Any,
    onShowSnackBar: suspend (String, String?) -> Boolean,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination
    ) {
        loginScreen(
            onLoginSuccess = {
                appState.navController.navigateToWorkspaces(
                    navOptions = navOptions {
                        popUpTo(LoginRoute) {
                            inclusive = true
                        }
                    }
                )
            }
        )
        workspacesScreen()
    }
}