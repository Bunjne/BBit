package com.bunjne.bbit.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.bunjne.bbit.domain.repository.WorkspaceRepository
import com.bunjne.bbit.ui.workspaceList.WorkspaceListViewModel
import com.bunjne.bbit.ui.workspaceList.WorkspaceScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

class WorkspaceListScreenNav: Screen {

    @Composable
    override fun Content() {
        val workspaceRepository: WorkspaceRepository = koinInject()
        val viewModel = getViewModel(
            key = "workspace-list-screen",
            factory = viewModelFactory {
                WorkspaceListViewModel(workspaceRepository)
            }
        )

        WorkspaceScreen(viewModel)
    }
}