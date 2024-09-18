package com.bunjne.bbit.ui.launches

import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.domain.ProgressBarState
import com.bunjne.bbit.domain.usecase.ViewState

data class LaunchesUIState(
    val launchesState: ViewState<List<RocketLaunch>> = ViewState.Loading(ProgressBarState.Loading),
    val selectedLaunch: RocketLaunch? = null
)