package com.bunjne.bbit.ui.workspaceList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunjne.bbit.data.remote.model.WorkspaceDto
import com.bunjne.bbit.ui.components.ProgressLoader

@Composable
fun WorkspaceScreen(
    viewModel: WorkspaceListViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            uiState.isLoading -> {
                ProgressLoader()
            }

            uiState.workspaceList.isNotEmpty() -> {
                WorkspaceList(uiState.workspaceList)
            }

            uiState.error.isNullOrEmpty().not() -> {

            }
        }
    }
}

@Composable
fun WorkspaceList(workspaceList: List<WorkspaceDto>) {
    LazyColumn {
        items(items = workspaceList, key = {
            it.workspace.uuid
        }) {
            WorkspaceItem(
                modifier = Modifier,
                workspace = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceItem(
    modifier: Modifier,
    workspace: WorkspaceDto
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .semantics {
                stateDescription = workspace.workspace.name
            },
        shape = RoundedCornerShape(2.dp),
        onClick = { }
    ) {
        Text(
            text = workspace.workspace.name,
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }

}