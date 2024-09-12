package com.bunjne.bbit.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.domain.repository.SpaceXRepository
import com.bunjne.bbit.ui.launches.LaunchesScreen
import com.bunjne.bbit.ui.launches.LaunchesViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject


class LaunchesScreenNav: Screen {

    @Composable
    override fun Content() {
        LaunchesScreen(
            viewModel = koinInject(),
            onLaunchClicked = {
                GoToLaunchDetailScreen(it)
            }
        )
    }

    @Composable
    private fun GoToLaunchDetailScreen(launch: RocketLaunch) {
        val navigator = LocalNavigator.currentOrThrow
        navigator.push(LaunchDetailScreenNav(launch))
    }
}