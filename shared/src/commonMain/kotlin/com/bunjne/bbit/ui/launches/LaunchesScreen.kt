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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bunjne.bbit.data.local.entity.RocketLaunch
import com.bunjne.bbit.domain.usecase.ViewState
import com.bunjne.bbit.ui.components.ProgressLoader
import com.bunjne.bbit.ui.theme.DefaultCornerShape


@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel,
    onLaunchClicked: @Composable (RocketLaunch) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    var selectedLaunchItem by remember {
        mutableStateOf<RocketLaunch?>(null)
    }

    LaunchedEffect(Unit) {
        viewModel.getLaunches()
    }

    /*GeneralViewContainer(
        modifier = Modifier.fillMaxSize(),
        uiComponent = UIComponent.None(""),
        progressBarState = uiState.launchesState as?
    )*/

    LaunchesView(uiState.launchesState) {
        viewModel.onUIEvent(LaunchesUIEvent.OnLaunchClicked(it))
    }

    uiState.selectedLaunch?.let { launch ->
        onLaunchClicked(launch)
//        GeneralDialog(
//            title = launch.missionName,
//            message = launch.details.orEmpty(),
//            positiveText = "Confirm",
//            negativeText = "Cancel",
//            onConfirmed = {},
//            onDismissed = { viewModel.onUIEvent(LaunchesUIEvent.OnLaunchClicked(null)) }
//        )
    }
}

@Composable
fun LaunchesView(
    launchesState: ViewState<List<RocketLaunch>>,
    onLaunchClicked: (RocketLaunch) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column {
        /*AmountSlider(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            sliderPosition = sliderPosition
        ) {
            sliderPosition = it
        }*/

        Text(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            text = "List of SpaceX launches",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp
        )

        when (launchesState) {
            is ViewState.Success -> {
                launchesState.data?.let { items ->
                    LaunchListView(items) { launch ->
                        onLaunchClicked(launch)
                    }
                }
            }

            is ViewState.Error -> {
                Text(text = launchesState.toString())
            }

            is ViewState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, // apply alignment to all children
                    modifier = Modifier.fillMaxSize()
                ) {
                    ProgressLoader()
                }
            }
        }
    }
}

@Composable
fun AmountSlider(
    modifier: Modifier = Modifier,
    sliderPosition: Float,
    onValueChange: (Float) -> Unit
) {
    val amountLimit = 100000
    val min = 0f
    val max = 100f
    val amountFactor = amountLimit / max
    val actualAmount by remember(sliderPosition) {
        mutableFloatStateOf(amountFactor * sliderPosition)
    }

    Column(modifier = modifier) {
        Row {
            IconButton(
                onClick = {
                    onValueChange(sliderPosition - 1)
                },
            ) {
                Icon(imageVector = Icons.Filled.RemoveCircle, "")
            }
            Slider(
                modifier = Modifier.weight(1f),
                value = sliderPosition,
                onValueChange = { onValueChange(it) },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                valueRange = min..max
            )

            IconButton(
                onClick = {
                    onValueChange(sliderPosition + 1)
                },
            ) {
                Icon(imageVector = Icons.Filled.AddCircle, "")
            }
        }

        Text(text = "On value changed: ${actualAmount.toInt()}")
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