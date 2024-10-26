package com.bunjne.bbit.ui.workspaceList

import androidx.compose.runtime.Immutable
import com.bunjne.bbit.data.model.Workspace
import com.bunjne.bbit.ui.model.UiText

@Immutable
data class WorkspacesUiState(
    var isLoading: Boolean = true,
    var error: UiText? = null,
    var workspaceList: List<Workspace> = emptyList(),
    var selectedWorkspace: Workspace? = null,
    var showInfoInDialog: Boolean = false
)