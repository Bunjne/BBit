package com.bunjne.bbit.ui.workspaceList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.data.remote.model.WorkspaceDto
import com.bunjne.bbit.ui.components.CustomToolbarScreen
import com.bunjne.bbit.ui.components.FullScreenLoadingDialog
import com.bunjne.bbit.ui.components.GeneralDialog

@Composable
fun WorkspacesView(
    viewModel: WorkspaceListViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    WorkspacesScreen(
        uiState = uiState,
        onUiAction = { viewModel.onAction(it) }
    )

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspacesScreen(
    uiState: WorkspacesUiState,
    onUiAction: (WorkspacesUiAction) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomToolbarScreen(
                title = "Workspace",
                isBack = false,
                onNavigationClicked = {}
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            when {
                uiState.isLoading -> {
                    FullScreenLoadingDialog()
                }

                uiState.workspaceList.isNotEmpty() -> {
                    WorkspaceList(
                        workspaceList = uiState.workspaceList,
                        onUiAction = onUiAction
                    )
                }

                uiState.error.isNullOrEmpty().not() -> {}
            }
        }
    }

    uiState.selectetWorkspace?.let {
        GeneralDialog(
            title = it.workspace.name,
            message = it.workspace.slug,
            positiveText = "Ok",
            negativeText = "Cancel",
            onConfirmed = { onUiAction(WorkspacesUiAction.OnWorkspaceUnSelected) },
            onDismissed = { onUiAction(WorkspacesUiAction.OnWorkspaceUnSelected) }
        )
    }
}

@Composable
fun WorkspaceList(
    workspaceList: List<WorkspaceDto>,
    onUiAction: (WorkspacesUiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = workspaceList,
            key = {
                it.workspace.uuid
            }
        ) {
            WorkspaceItem(
                modifier = Modifier,
                workspace = it,
                onCardClicked = { workspace ->
                    onUiAction(WorkspacesUiAction.OnWorkspaceClicked(workspace))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceItem(
    modifier: Modifier,
    workspace: WorkspaceDto,
    onCardClicked: (WorkspaceDto) -> Unit
) {
    Card(
        modifier = modifier
            .semantics {
                stateDescription = workspace.workspace.name
            },
        shape = RoundedCornerShape(8.dp),
        onClick = { onCardClicked(workspace) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = workspace.workspace.name,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}