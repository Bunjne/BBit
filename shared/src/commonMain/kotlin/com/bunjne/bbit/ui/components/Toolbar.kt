package com.bunjne.bbit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbarScreen(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ),
    title: String,
    isBack: Boolean,
    onNavigationClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = topAppBarScrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
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
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "backIcon")
                }
            } else {
                IconButton(onClick = {
                    //TODO Do something
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "menuIcon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}