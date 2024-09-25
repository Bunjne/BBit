package com.bunjne.bbit.util.extension

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

fun NavController.safePopBackstack(
    route: Any? = null,
    inclusive: Boolean = false,
    saveState: Boolean = false
) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        route?.let {
            popBackStack(
                route = it,
                inclusive = inclusive,
                saveState = saveState
            )
        } ?: run {
            popBackStack()
        }
    }
}