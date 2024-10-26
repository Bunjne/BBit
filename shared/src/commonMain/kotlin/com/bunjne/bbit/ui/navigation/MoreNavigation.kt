package com.bunjne.bbit.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.bunjne.bbit.ui.util.animatedComposable
import kotlinx.serialization.Serializable

@Serializable
data object MoreRoute

fun NavController.navigateToMore(navOptions: NavOptions? = null) =
    navigate(route = MoreRoute, navOptions = navOptions)


fun NavGraphBuilder.moreScreen() {
    animatedComposable<MoreRoute> {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "More Screen",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}