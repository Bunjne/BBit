package com.bunjne.bbit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bunjne.bbit.ui.navigation.MoreRoute
import com.bunjne.bbit.ui.navigation.TopLevelNavItem
import com.bunjne.bbit.ui.navigation.WorkspacesRoute
import com.bunjne.bbit.ui.navigation.navigateToMore
import com.bunjne.bbit.ui.navigation.navigateToWorkspaces
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberBBitAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): BBitAppState {
    return remember(navController, coroutineScope) {
        BBitAppState(navController, coroutineScope)
    }
}

@Stable
class BBitAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelNavItems: List<TopLevelNavItem> = TopLevelNavItem.entries

    val shouldShowNavigationBar :Boolean @Composable get() = currentDestination?.hierarchy?.any { destination ->
        destination.hasRoute(WorkspacesRoute::class) || destination.hasRoute(MoreRoute::class)
    } ?: false

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelNavItem) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelNavItem.Workspaces -> navController.navigateToWorkspaces(topLevelNavOptions)
            TopLevelNavItem.More -> navController.navigateToMore(topLevelNavOptions)
        }
    }
}