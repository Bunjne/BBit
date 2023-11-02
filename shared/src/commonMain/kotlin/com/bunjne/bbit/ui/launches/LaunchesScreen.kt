package com.bunjne.bbit.ui.launches

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.ui.components.GeneralDialog
import com.bunjne.bbit.ui.components.ProgressLoader
import com.bunjne.bbit.ui.theme.DefaultCornerShape
import com.bunjne.bbit.usecase.ViewState


@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel
) {
    viewModel.getLaunches()
    val launchesViewState = viewModel.launchesViewState.collectAsState()
    var selectedLaunchItem by remember {
        mutableStateOf<RocketLaunch?>(null)
    }

    selectedLaunchItem?.let { launch ->
        GeneralDialog(
            title = launch.missionName,
            message = launch.details.orEmpty(),
            positiveText = "Confirm",
            negativeText = "Cancel",
            onConfirmed = {},
            onDismissed = { selectedLaunchItem = null }
        )
    }

    launchesViewState.value.let {
        when (it) {
            is ViewState.Success -> {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        text = "List of SpaceX launches",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                    it.data?.let { items ->
                        LaunchListView(items) { launch ->
                            selectedLaunchItem = launch
                        }
                    }
                }
            }

            is ViewState.Error -> {
                Text(text = it.toString())
            }

            is ViewState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, // you apply alignment to all children
                    modifier = Modifier.fillMaxSize()
                ) {
                    ProgressLoader()
                }
            }
        }
    }
}

@Composable
fun LaunchListView(items: List<RocketLaunch>, onClick: (RocketLaunch) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items
        ) { launch ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(DefaultCornerShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        onClick(launch)
                    }
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LaunchItemView(launch = launch)
            }
        }
    }
}

@Composable
fun LaunchItemView(launch: RocketLaunch) {
    Text(
        text = launch.missionName,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Text(
        text = "#${launch.flightNumber}",
        color = MaterialTheme.colorScheme.onPrimary
    )
}