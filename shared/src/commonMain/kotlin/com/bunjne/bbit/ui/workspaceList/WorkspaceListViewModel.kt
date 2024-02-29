package com.bunjne.bbit.ui.workspaceList

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkspaceListViewModel(
    private val workspaceRepository: WorkspaceRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(WorkspaceUIState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        fetchWorkspaceList()
    }

    private fun fetchWorkspaceList() {
        viewModelScope.launch {
            when (val response = workspaceRepository.getWorkspaces(1)) {
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, workspaceList = response.data)
                    }
                }
                is DataState.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }
            }
        }
    }
}