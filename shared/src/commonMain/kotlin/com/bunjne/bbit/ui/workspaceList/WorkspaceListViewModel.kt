package com.bunjne.bbit.ui.workspaceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.model.Workspace
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import com.bunjne.bbit.ui.util.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkspaceListViewModel(
    private val workspaceRepository: WorkspaceRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkspacesUiState())
    val uiState = _uiState.asStateFlow()

    // Another option which depends on use cases
    /*val uiState = _uiState.onStart {
        fetchWorkspaceList(true)
        observeWorkspaceList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WorkspacesUiState()
    )*/

    private val _uiEvent by lazy { Channel<WorkspacesUiEvent>() }
    val uiEvent: Flow<WorkspacesUiEvent> by lazy { _uiEvent.receiveAsFlow() }

    private var currentWorkspacePage = 0

    init {
        fetchWorkspaceList(true)
        observeWorkspaceList()
    }

    private fun fetchWorkspaceList(isRefresh: Boolean) {
        if (isRefresh) {
            currentWorkspacePage = 0
        }
        currentWorkspacePage += 1

        viewModelScope.launch {
            workspaceRepository.fetchWorkspaces(currentWorkspacePage)
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
                .collectLatest { response ->
                    when (response) {
                        is Result.Success -> _uiState.update {
                            it.copy(isLoading = false)
                        }

                        is Result.Error -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = response.error.asUiText()
                            )
                        }
                    }
                }
        }
    }

    private fun observeWorkspaceList() {
        viewModelScope.launch {
            workspaceRepository.getWorkspaces()
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
                .collectLatest { response ->
                    when (response) {
                        is Result.Success -> _uiState.update {
                            it.copy(
                                workspaceList = response.data
                            )
                        }

                        is Result.Error -> _uiState.update {
                            it.copy(
                                error = response.error.asUiText()
                            )
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