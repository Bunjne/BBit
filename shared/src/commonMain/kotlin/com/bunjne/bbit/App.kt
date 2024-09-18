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
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.bunjne.bbit.extension.initNav
import com.bunjne.bbit.ui.navigation.LoginScreenNav
import com.bunjne.bbit.ui.navigation.WorkspaceListScreenNav
import com.bunjne.bbit.ui.theme.BBitTheme
import okio.FileSystem
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    topSafeArea: Float,
    bottomSafeArea: Float
) {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    KoinContext {
        val viewModel: BBitAppViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()

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

private fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

private fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes((512L * 1024 * 1024) / 512)
            .build()
}