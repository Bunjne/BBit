package com.bunjne.bbit.ui.workspaceList

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.data.remote.model.WorkspaceDto
import com.bunjne.bbit.ui.components.GeneralDialog
import com.bunjne.bbit.ui.components.ProgressLoader

@Composable
fun WorkspaceScreen(
    viewModel: WorkspaceListViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var workspaceSelected by remember {
        mutableStateOf<WorkspaceDto?>(null)
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            uiState.isLoading -> {
                ProgressLoader()
            }

            uiState.workspaceList.isNotEmpty() -> {
                WorkspaceList(
                    workspaceList = uiState.workspaceList,
                    onCardClicked = {
                        workspaceSelected = it
                    }
                )
            }

            uiState.error.isNullOrEmpty().not() -> {

            }
        }

        workspaceSelected?.let {
            GeneralDialog(
                title = it.workspace.name,
                message = it.workspace.slug,
                positiveText = "Confirm",
                negativeText = "Cancel",
                onConfirmed = {},
                onDismissed = {}
            )
        }
    }
}

@Composable
fun WorkspaceList(
    workspaceList: List<WorkspaceDto>,
    onCardClicked: (WorkspaceDto) -> Unit
) {
    LazyColumn {
        items(
            items = workspaceList,
            key = {
                it.workspace.uuid
            }
        ) {
            WorkspaceItem(
                modifier = Modifier,
                workspace = it,
                onCardClicked = onCardClicked
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
        colors = CardDefaults.cardColors(containerColor = Color.Blue)
    ) {
        Text(
            text = workspace.workspace.name,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }

}