package com.bunjne.bbit.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveAsEvent(
    flow: Flow<T>,
    eventMap: Map<T, () -> Unit>,
    lifecycle: Lifecycle.State = Lifecycle.State.STARTED,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycleScope, flow) {
        lifecycleOwner.repeatOnLifecycle(lifecycle) {
            withContext(dispatcher) {
                flow.collect { event ->
                    eventMap[event]?.invoke()
                }
            }
        }
    }
}