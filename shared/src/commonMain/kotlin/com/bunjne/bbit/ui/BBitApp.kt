package com.bunjne.bbit.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.bunjne.bbit.ui.components.BBitNavigationSuiteScaffold
import com.bunjne.bbit.ui.components.navItem
import com.bunjne.bbit.ui.navigation.BBitNavHost
import com.bunjne.bbit.ui.navigation.LoginRoute
import com.bunjne.bbit.ui.navigation.TopLevelNavItem
import com.bunjne.bbit.ui.navigation.WorkspacesRoute
import com.bunjne.bbit.ui.theme.BBitTheme
import okio.FileSystem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
) {
    KoinContext {
        val snackBarHostState = remember { SnackbarHostState() }
        val windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()

        val viewModel: BBitAppViewModel = koinInject()
        val appState = rememberBBitAppState()
        val currentDestination = appState.currentDestination
        val shouldShowNavBar = appState.shouldShowNavigationBar
        val startDestination: Any = remember {
            if (viewModel.isLoggedIn()) {
                WorkspacesRoute
            } else {
                LoginRoute
            }
        }
        val navigationSuiteType by remember(shouldShowNavBar) {
            derivedStateOf {
                if (shouldShowNavBar) {
                    NavigationSuiteScaffoldDefaults
                        .calculateFromAdaptiveInfo(windowAdaptiveInfo)
                } else {
                    NavigationSuiteType.None
                }
            }
        }

        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }
        BBitTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            BBitNavigationSuiteScaffold(
                navigationSuiteItems = { navigationSuiteItemColors ->
                    appState.topLevelNavItems.forEach { destination ->
                        val selected =
                            currentDestination.isTopLevelDestinationInHierarchy(destination)

                        navigationSuiteItem(
                            destination = destination,
                            selected = selected,
                            onNavigateToDestination = {
                                if (!selected) {
                                    appState.navigateToTopLevelDestination(destination)
                                }
                            },
                            navigationSuiteItemColors = navigationSuiteItemColors
                        )
                    }
                },
                navigationSuiteType = navigationSuiteType
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .imePadding(),
                ) {
                    BBitNavHost(
                        modifier = Modifier.weight(1f),
                        appState = appState,
                        startDestination = startDestination,
                        onShowSnackBar = { message, action ->
                            snackBarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = SnackbarDuration.Short
                            ) == SnackbarResult.ActionPerformed
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .shadow(
                                elevation = 6.dp,
                                spotColor = MaterialTheme.colorScheme.onBackground,
                                ambientColor = Color.Transparent
                            )
                    )
                }
            }
        }
    }
}

private fun NavigationSuiteScope.navigationSuiteItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    destination: TopLevelNavItem,
    onNavigateToDestination: () -> Unit,
    navigationSuiteItemColors: NavigationSuiteItemColors
) {
    navItem(
        selected = selected,
        onClick = { onNavigateToDestination() },
        icon = {
            Icon(
                imageVector = destination.icon,
                contentDescription = "${destination.title} icon",
            )
        },
        label = { Text(stringResource(destination.title)) },
        modifier = modifier.testTag("NavItem"),
        navigationSuiteItemColors = navigationSuiteItemColors
    )
}

private fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED).diskCache {
            newDiskCache()
        }.crossfade(true).logger(DebugLogger()).build()

private fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes((512L * 1024 * 1024) / 512)
        .build()
}

/**
 * Checking if the current destination hierarchy contains a top level destination
 * e.g., Used for showing a selected icon and title
 */
private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelNavItem) =
    this?.hierarchy?.any { it.hasRoute(destination.route::class) } == true