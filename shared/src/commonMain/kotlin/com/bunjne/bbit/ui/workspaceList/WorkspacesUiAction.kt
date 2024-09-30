package com.bunjne.bbit.ui.workspaceList

import com.bunjne.bbit.domain.model.Workspace

sealed interface WorkspacesUiAction {
    data class OnWorkspaceClicked(val workspace: Workspace) : WorkspacesUiAction
    data object OnWorkspaceUnSelected : WorkspacesUiAction
    data object OnErrorCanceled : WorkspacesUiAction
    data object OnInfoClicked : WorkspacesUiAction
}
