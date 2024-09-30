package com.bunjne.bbit.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.bunjne.bbit.util.extension.animatedComposable
import kotlinx.serialization.Serializable

@Serializable
data object MoreRoute

fun NavController.navigateToMore(navOptions: NavOptions? = null) =
    navigate(route = MoreRoute, navOptions = navOptions)


fun NavGraphBuilder.moreScreen() {
    animatedComposable<MoreRoute> {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Blue)
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(20.dp).background(Color.Red).align(
                Alignment.BottomCenter))
        }
    }
}