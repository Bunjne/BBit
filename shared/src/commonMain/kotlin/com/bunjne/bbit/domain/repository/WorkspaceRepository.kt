package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.remote.model.WorkspaceDto

interface WorkspaceRepository {
    suspend fun getWorkspaces(page: Int): DataState<List<WorkspaceDto>>
}