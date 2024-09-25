package com.bunjne.bbit.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.bunjne.bbit.ui.login.LoginRoute
import com.bunjne.bbit.util.extension.animatedComposable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(route = LoginRoute, navOptions = navOptions)

fun NavGraphBuilder.loginScreen(onLoginSuccess: () -> Unit) {
    animatedComposable<LoginRoute> {
        LoginRoute(
            viewModel = koinViewModel(),
            onLoginSuccess = onLoginSuccess
        )
    }
}