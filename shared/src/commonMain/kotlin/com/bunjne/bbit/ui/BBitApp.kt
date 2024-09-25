package com.bunjne.bbit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.bunjne.bbit.ui.navigation.BBitNavHost
import com.bunjne.bbit.ui.navigation.LoginRoute
import com.bunjne.bbit.ui.navigation.TopLevelNavItem
import com.bunjne.bbit.ui.navigation.TopLevelRoute
import com.bunjne.bbit.ui.navigation.WorkspacesRoute
import com.bunjne.bbit.ui.theme.BBitTheme
import okio.FileSystem
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    KoinContext {
        val viewModel: BBitAppViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        val appState = rememberBBitAppState()
        val snackBarHostState = remember { SnackbarHostState() }

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
                if (!uiState.isLoading) {
                    BBitNavHost(
                        appState = appState,
                        startDestination = if (uiState.refreshToken.isNullOrEmpty()) {
                            LoginRoute
                        } else {
                            WorkspacesRoute
                        },
                        onShowSnackBar = { message, action ->
                            snackBarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = SnackbarDuration.Short
                            ) == SnackbarResult.ActionPerformed
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomAppBar(
    modifier: Modifier = Modifier,
    topLevelRoutes: List<TopLevelRoute<*>>,
) {

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