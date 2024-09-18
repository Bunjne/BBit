package com.bunjne.bbit.ui.launches

import com.bunjne.bbit.data.local.entity.RocketLaunch

sealed class LaunchesUIEvent {
    data class OnLaunchClicked(val launch: RocketLaunch?): LaunchesUIEvent()
}
