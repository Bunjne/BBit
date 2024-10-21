package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.model.Workspace

interface WorkspaceRepository {
    suspend fun getWorkspaces(page: Int): DataState<List<Workspace>>
}
