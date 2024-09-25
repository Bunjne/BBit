package com.bunjne.bbit.ui.workspaceList

import androidx.compose.runtime.Immutable
import com.bunjne.bbit.data.remote.model.WorkspaceDto

@Immutable
data class WorkspacesUiState(
    var isLoading: Boolean = true,
    var error: String? = null,
    var workspaceList: List<WorkspaceDto> = emptyList(),
    var selectedWorkspace: WorkspaceDto? = null,
    var showInfoInDialog: Boolean = false
)