package com.bunjne.bbit.ui.workspaceList

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.remote.model.WorkspaceDto
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkspaceListViewModel(
    private val workspaceRepository: WorkspaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkspacesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent by lazy { Channel<WorkspacesUiEvent>() }
    val uiEvent: Flow<WorkspacesUiEvent> by lazy { _uiEvent.receiveAsFlow() }

    init {
        fetchWorkspaceList()
    }

    private fun fetchWorkspaceList() {
        viewModelScope.launch {
            when (val response = workspaceRepository.getWorkspaces(1)) {
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workspaceList = response.data
                        )
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

    fun onAction(action: WorkspacesUiAction) {
        when (action) {
            is WorkspacesUiAction.OnWorkspaceClicked -> ::handleOnWorkspaceCLicked.invoke(action.workspace)
            is WorkspacesUiAction.OnWorkspaceUnSelected -> ::handleOnWorkspaceUnSelected.invoke()
        }
    }

    private fun handleOnWorkspaceCLicked(workspace: WorkspaceDto) {
        _uiState.update { state -> state.copy(selectetWorkspace = workspace) }
        viewModelScope.launch {
            _uiEvent.send(WorkspacesUiEvent.ShowWorkspaceInfo)
        }
    }

    private fun handleOnWorkspaceUnSelected() {
        _uiState.update { state -> state.copy(selectetWorkspace = null) }
    }
}