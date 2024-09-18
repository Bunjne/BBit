package com.bunjne.bbit.ui.launchdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bunjne.bbit.data.local.entity.RocketLaunch

@Composable
fun LaunchDetail(launch: RocketLaunch) {
    Text("Mission Name: ${launch.missionName}")
}