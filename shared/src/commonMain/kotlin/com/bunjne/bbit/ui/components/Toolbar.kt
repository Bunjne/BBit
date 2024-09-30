package com.bunjne.bbit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbarScreen(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
    title: String,
    isBack: Boolean,
    onNavigationClicked: () -> Unit = {},
    toolbarActions: List<ToolbarActionType> = emptyList(),
    onActionClicked: (ToolbarActionType) -> Unit = {},
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
    )
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = topAppBarScrollBehavior,
        colors = topAppBarColors,
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            if (isBack) {
                IconButton(onClick = { onNavigationClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backIcon"
                    )
                }
            }
        },
        actions = {
            toolbarActions.forEach { action ->
                IconButton(
                    onClick = {
                        onActionClicked(action)
                    }
                ) {
                    Icon(
                        imageVector = action.icon,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "${action.name}Action",
                    )
                }
            }
        }
    )
}

enum class ToolbarActionType(
    val icon: ImageVector
) {
    INFO(Icons.Filled.Info)
}