package com.bunjne.bbit.ui.workspaceList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.bunjne.bbit.data.model.Workspace
import com.bunjne.bbit.resources.Res
import com.bunjne.bbit.resources.general_cancel
import com.bunjne.bbit.resources.general_ok
import com.bunjne.bbit.resources.workspaces_dialog_description
import com.bunjne.bbit.resources.workspaces_dialog_title
import com.bunjne.bbit.resources.workspaces_empty_list
import com.bunjne.bbit.resources.workspaces_title
import com.bunjne.bbit.ui.components.CustomToolbarScreen
import com.bunjne.bbit.ui.components.ErrorPopup
import com.bunjne.bbit.ui.components.FullScreenLoadingDialog
import com.bunjne.bbit.ui.components.PlatformPopupDialog
import com.bunjne.bbit.ui.components.ToolbarActionType
import org.jetbrains.compose.resources.stringResource

private val actionList = listOf(ToolbarActionType.INFO)

@Composable
fun WorkspacesRoute(
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
internal fun WorkspacesScreen(
    uiState: WorkspacesUiState,
    onUiAction: (WorkspacesUiAction) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomToolbarScreen(
                title = stringResource(Res.string.workspaces_title),
                isBack = false,
                toolbarActions = actionList,
                onActionClicked = { action ->
                    if (action == ToolbarActionType.INFO) {
                        onUiAction(WorkspacesUiAction.OnInfoClicked)
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                uiState.isLoading -> FullScreenLoadingDialog()
                else -> {
                    WorkspaceList(
                        workspaceList = uiState.workspaceList,
                        onUiAction = onUiAction
                    )
                }
            }
        }

        uiState.error?.let { error ->
            ErrorPopup(
                error = error.asString(),
                onDismiss = {
                    onUiAction(WorkspacesUiAction.OnErrorCanceled)
                }
            )
        }

        uiState.selectedWorkspace?.let { workspace ->
            PlatformPopupDialog(
                title = workspace.name,
                message = workspace.slug,
                positiveText = stringResource(Res.string.general_ok),
                negativeText = stringResource(Res.string.general_cancel),
                onConfirmed = { onUiAction(WorkspacesUiAction.OnWorkspaceUnSelected) },
                onDismissed = { onUiAction(WorkspacesUiAction.OnWorkspaceUnSelected) }
            )
        }

        if (uiState.showInfoInDialog) {
            PlatformPopupDialog(
                title = stringResource(Res.string.workspaces_dialog_title),
                message = stringResource(Res.string.workspaces_dialog_description),
                positiveText = stringResource(Res.string.general_ok),
                onConfirmed = { onUiAction(WorkspacesUiAction.OnInfoClicked) },
            )
        }
    }
}

@Composable
internal fun WorkspaceList(
    workspaceList: List<Workspace>,
    onUiAction: (WorkspacesUiAction) -> Unit
) {
    if (workspaceList.isEmpty()) {
        WorkspaceEmptyList()
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = workspaceList,
            key = {
                it.uuid
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

@Composable
private fun WorkspaceItem(
    modifier: Modifier,
    workspace: Workspace,
    onCardClicked: (Workspace) -> Unit
) {
    Card(
        modifier = modifier
            .semantics {
                stateDescription = workspace.name
            },
        shape = RoundedCornerShape(8.dp),
        onClick = { onCardClicked(workspace) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Card(
                modifier = Modifier.wrapContentSize(),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp),
                    model = workspace.profileImage,
                    error = {
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            textAlign = TextAlign.Center,
                            text = workspace.name.first().uppercase(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    contentDescription = "Workspace profile image ${workspace.uuid}",
                )
            }

            Text(
                text = workspace.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
private fun WorkspaceEmptyList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.workspaces_empty_list),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}