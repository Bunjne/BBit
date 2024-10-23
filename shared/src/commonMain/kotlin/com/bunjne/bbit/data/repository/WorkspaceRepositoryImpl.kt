package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.local.dao.WorkspaceDao
import com.bunjne.bbit.data.local.entity.toEntity
import com.bunjne.bbit.data.model.Workspace
import com.bunjne.bbit.data.model.toExternalModel
import com.bunjne.bbit.data.remote.service.WorkspaceService
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WorkspaceRepositoryImpl(
    private val api: WorkspaceService,
    private val workspaceDao: WorkspaceDao
) : BaseRepository(), WorkspaceRepository {

    override suspend fun fetchWorkspaces(page: Int): Flow<Result<Any>> =
        flow {
            emit(
                execute {
                    api.getCurrentWorkspaces(page).values.also { workspaces ->
                        workspaceDao.insert(workspaces.map { it.toEntity() })
                    }
                }
            )
        }

    override suspend fun getWorkspaces(): Flow<Result<List<Workspace>>> =
        workspaceDao.getAllWorkspaces().map { workspaceEntities ->
            Result.Success(workspaceEntities.map { it.toExternalModel() })
        }

    override suspend fun clearAllWorkspaces() {
        workspaceDao.clearAll()
    }
}