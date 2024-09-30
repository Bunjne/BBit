package com.bunjne.bbit.domain.usecase

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.domain.model.Workspace
import com.bunjne.bbit.domain.model.toWorkspace
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWorkspacesUseCase(
    private val workspaceRepository: WorkspaceRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(page: Int): DataState<List<Workspace>> = withContext(dispatcher) {
        when (val result = workspaceRepository.getWorkspaces(page)) {
            is DataState.Success -> {
                DataState.Success(result.data.map { it.toWorkspace() })
            }

            is DataState.Error -> result
        }
    }
}