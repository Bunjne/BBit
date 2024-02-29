package com.bunjne.bbit.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.ui.launchdetail.LaunchDetail

class LaunchDetailScreenNav(private val launch: RocketLaunch): Screen {

    @Composable
    override fun Content() {
        LaunchDetail(launch)
    }
}