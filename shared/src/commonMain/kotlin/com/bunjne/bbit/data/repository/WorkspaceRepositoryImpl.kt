package com.bunjne.bbit.data.repository

import com.bunjne.bbit.braodcaster.NetworkManager
import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.local.dao.WorkspaceDao
import com.bunjne.bbit.data.local.entity.toEntity
import com.bunjne.bbit.data.model.Workspace
import com.bunjne.bbit.data.model.toExternalModel
import com.bunjne.bbit.data.remote.service.WorkspaceService
import com.bunjne.bbit.domain.repository.WorkspaceRepository

class WorkspaceRepositoryImpl(
    private val api: WorkspaceService,
    private val workspaceDao: WorkspaceDao,
    networkManager: NetworkManager
) : BaseRepository(networkManager), WorkspaceRepository {

    override suspend fun getWorkspaces(page: Int): DataState<List<Workspace>> {
        return execute {
            api.getCurrentWorkspaces(page).values.also { workspaces ->
                workspaceDao.insert(workspaces.map { it.toEntity() })
            }

            workspaceDao.getAllWorkspaces().map { it.toExternalModel() }
        }
    }
}