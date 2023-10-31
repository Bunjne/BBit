package com.bunjne.bbit.presentation.launches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            is ViewState.Success -> {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        text = "List of SpaceX launches",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 24.sp
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = it.data!!
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                    .background(
                                        MaterialTheme.colorScheme.onPrimaryContainer,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = it.missionName,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                                Text(
                                    text = "#${it.flightNumber}",
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
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