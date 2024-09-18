package com.bunjne.bbit.ui.workspaceList

import com.bunjne.bbit.data.remote.model.WorkspaceDto

sealed interface WorkspacesUiAction {
    data class OnWorkspaceClicked(val workspace: WorkspaceDto) : WorkspacesUiAction
    data object OnWorkspaceUnSelected : WorkspacesUiAction
    data object OnErrorCanceled : WorkspacesUiAction
    data object OnInfoClicked : WorkspacesUiAction
}
