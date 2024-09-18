package com.bunjne.bbit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunjne.bbit.domain.ProgressBarState
import com.bunjne.bbit.domain.UIComponent

@Composable
fun GeneralViewContainer(
    modifier: Modifier = Modifier,
    uiComponent: UIComponent,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            content()

            when (uiComponent) {
                is UIComponent.Dialog -> {
                    GeneralPopupDialog(
                        title = uiComponent.title,
                        message = uiComponent.description,
                        positiveText = uiComponent.positiveText,
                        negativeText = uiComponent.negativeText,
                        onConfirmed = uiComponent.onSuccess,
                        onDismissed = uiComponent.onDismiss
                    )
                }
                else -> {} //TODO
            }

            if (progressBarState is ProgressBarState.Loading) {
                ProgressLoader()
            }
        }
    }
}