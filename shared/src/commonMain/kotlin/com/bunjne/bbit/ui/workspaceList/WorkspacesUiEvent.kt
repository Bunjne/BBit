package com.bunjne.bbit.ui.workspaceList

sealed interface WorkspacesUiEvent {
    data object ShowWorkspaceInfo: WorkspacesUiEvent
}