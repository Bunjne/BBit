package com.bunjne.bbit.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.bunjne.bbit.ui.more.MoreRoute
import com.bunjne.bbit.ui.util.animatedComposable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object MoreRoute

fun NavController.navigateToMore(navOptions: NavOptions? = null) =
    navigate(route = MoreRoute, navOptions = navOptions)


fun NavGraphBuilder.moreScreen(onSignOutSuccess: () -> Unit) {
    animatedComposable<MoreRoute> {
        MoreRoute(
            viewModel = koinViewModel(),
            onSignOutSuccess = onSignOutSuccess
        )
    }
}