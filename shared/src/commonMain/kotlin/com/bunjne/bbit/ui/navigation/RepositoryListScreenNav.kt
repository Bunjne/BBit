package com.bunjne.bbit.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.bunjne.bbit.ui.repositoryList.RepositoryList

class RepositoryListScreenNav: Screen {

    @Composable
    override fun Content() {
        RepositoryList()
    }
}