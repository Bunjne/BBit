package com.bunjne.bbit.ui.workspaceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunjne.bbit.braodcaster.NetworkManager
import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.model.Workspace
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkspaceListViewModel(
    private val workspaceRepository: WorkspaceRepository,
    private val networkManager: NetworkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkspacesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    // Another option which depends on use cases
    /*val uiState = _uiState.onStart {
        fetchWorkspaceList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WorkspacesUiState()
    )*/

    private val _uiEvent by lazy { Channel<WorkspacesUiEvent>() }
    val uiEvent: Flow<WorkspacesUiEvent> by lazy { _uiEvent.receiveAsFlow() }


    init {
        fetchWorkspaceList()
        viewModelScope.launch {
            networkManager.networkStatusFlow.collectLatest { hasNetwork ->
                if (!uiState.value.error.isNullOrEmpty() && hasNetwork) {
                    fetchWorkspaceList()
                }
            }
        }
    }

    private fun fetchWorkspaceList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            when (val response = workspaceRepository.getWorkspaces(1)) {
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workspaceList = response.data,
                            error = null
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
            is WorkspacesUiAction.OnErrorCanceled -> ::handleOnErrorCanceled.invoke()
            is WorkspacesUiAction.OnInfoClicked -> ::handleOnWorkspaceInfoClicked.invoke()

        }
    }

    private fun handleOnWorkspaceCLicked(workspace: Workspace) {
        _uiState.update { state -> state.copy(selectedWorkspace = workspace) }
    }

    private fun handleOnWorkspaceUnSelected() {
        _uiState.update { state -> state.copy(selectedWorkspace = null) }
    }

    private fun handleOnErrorCanceled() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    private fun handleOnWorkspaceInfoClicked() {
        _uiState.update {
            it.copy(showInfoInDialog = !it.showInfoInDialog)
        }
    }
}