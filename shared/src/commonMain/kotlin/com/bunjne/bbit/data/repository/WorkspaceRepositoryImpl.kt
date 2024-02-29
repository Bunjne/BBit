package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.remote.model.WorkspaceDto
import com.bunjne.bbit.data.remote.service.WorkspaceService
import com.bunjne.bbit.domain.repository.WorkspaceRepository

class WorkspaceRepositoryImpl(
    private val api: WorkspaceService
) : BaseRepository(), WorkspaceRepository {

    override suspend fun getWorkspaces(page: Int): DataState<List<WorkspaceDto>> =
        execute {
            api.getCurrentWorkspaces(page).values
        }
}