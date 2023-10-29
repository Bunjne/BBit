package com.bunjne.bbit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunjne.bbit.presentation.launches.LaunchesScreen
import com.bunjne.bbit.presentation.launches.LaunchesViewModel
import com.bunjne.bbit.usecase.repository.SpaceXRepository
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            val spaceXRepository: SpaceXRepository = koinInject()
//            val viewModel = getViewModel(
//                key = "launch-list-screen",
//                factory = viewModelFactory {
//                    LaunchesViewModel(spaceXRepository)
//                }
//            )
//
//            LaunchesScreen(viewModel = viewModel)
        }
    }
}