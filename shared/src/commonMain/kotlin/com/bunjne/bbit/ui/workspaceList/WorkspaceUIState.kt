package com.bunjne.bbit.ui.workspaceList

import com.bunjne.bbit.data.remote.model.WorkspaceDto

data class WorkspaceUIState(
    var isLoading: Boolean = false,
    var error: String? = null,
    var workspaceList: List<WorkspaceDto> = emptyList()
)