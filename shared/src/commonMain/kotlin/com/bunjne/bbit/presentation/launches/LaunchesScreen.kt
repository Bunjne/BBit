package com.bunjne.bbit.presentation.launches

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.usecase.ViewState

@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel
) {
    viewModel.getLaunches()
    val launchesViewState = viewModel.launchesViewState.collectAsState()

    launchesViewState.value.let {
        when (it) {
            is ViewState.Success -> LazyColumn(
                contentPadding = PaddingValues(12.dp)
            ) {
                items(
                    items = it.data!!
                ) {
                    Text(text = it.missionName)
                }
            }

            else -> {

            }
        }
    }
}

@Composable
fun LaunchListView(items: List<RocketLaunch>) {

}