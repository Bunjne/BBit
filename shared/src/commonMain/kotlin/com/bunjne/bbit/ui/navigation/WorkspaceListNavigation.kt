package com.bunjne.bbit.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.bunjne.bbit.ui.workspaceList.WorkspacesRoute
import com.bunjne.bbit.util.extension.animatedComposable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object WorkspacesRoute

fun NavController.navigateToWorkspaces(navOptions: NavOptions? = null) =
    navigate(route = WorkspacesRoute, navOptions = navOptions)


fun NavGraphBuilder.workspacesScreen() {
    animatedComposable<WorkspacesRoute> {
        WorkspacesRoute(
            viewModel = koinViewModel()
        )
    }
}