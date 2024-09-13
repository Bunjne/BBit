package com.bunjne.bbit.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bunjne.bbit.domain.repository.AuthRepository
import com.bunjne.bbit.ui.login.LoginScreen
import com.bunjne.bbit.ui.login.LoginViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

class LoginScreenNav : Screen {

    @Composable
    override fun Content() {
        val loginWorkspace: AuthRepository = koinInject()
        val viewModel = getViewModel(
            key = "launch-list-screen",
            factory = viewModelFactory {
                LoginViewModel(loginWorkspace)
            }
        )

        LoginScreen(
            viewModel = viewModel,
            onLoginSuccess = {
                GoToWorkspaceListScreen()
            }
        )
    }

    @Composable
    private fun GoToWorkspaceListScreen() {
        val navigator = LocalNavigator.currentOrThrow
        navigator.push(WorkspaceListScreenNav())
    }
}