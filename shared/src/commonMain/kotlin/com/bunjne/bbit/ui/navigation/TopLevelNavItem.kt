package com.bunjne.bbit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.bunjne.bbit.resources.Res
import com.bunjne.bbit.resources.more_title
import com.bunjne.bbit.resources.workspaces_title
import org.jetbrains.compose.resources.StringResource

enum class TopLevelNavItem(
    val title: StringResource, val route: Any, val icon: ImageVector
) {
    Workspaces(
        title = Res.string.workspaces_title,
        route = WorkspacesRoute,
        icon = Icons.Filled.Home
    ),
    More(
        title = Res.string.more_title,
        route = MoreRoute,
        icon = Icons.Filled.Settings
    )
}