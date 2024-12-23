package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.model.Workspace
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {

    fun fetchWorkspaces(page: Int): Flow<Result<Any>>

    fun getWorkspaces(): Flow<Result<List<Workspace>>>

    suspend fun clearAllWorkspaces()
}
