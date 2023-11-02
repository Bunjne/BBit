package com.bunjne.bbit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.ui.launches.LaunchesScreen
import com.bunjne.bbit.ui.launches.LaunchesViewModel
import com.bunjne.bbit.ui.theme.BBitTheme
import com.bunjne.bbit.usecase.repository.SpaceXRepository
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    topSafeArea: Float,
    bottomSafeArea: Float
) {
    KoinContext {
        BBitTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = topSafeArea.dp, bottom = bottomSafeArea.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                val spaceXRepository: SpaceXRepository = koinInject()
                val viewModel = getViewModel(
                    key = "launch-list-screen",
                    factory = viewModelFactory {
                        LaunchesViewModel(spaceXRepository)
                    }
                )
                LaunchesScreen(viewModel = viewModel)
            }
        }
    }
}