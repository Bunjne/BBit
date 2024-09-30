package com.bunjne.bbit.ui.workspaceList

import androidx.compose.runtime.Immutable
import com.bunjne.bbit.domain.model.Workspace

@Immutable
data class WorkspacesUiState(
    var isLoading: Boolean = true,
    var error: String? = null,
    var workspaceList: List<Workspace> = emptyList(),
    var selectedWorkspace: Workspace? = null,
    var showInfoInDialog: Boolean = false
)