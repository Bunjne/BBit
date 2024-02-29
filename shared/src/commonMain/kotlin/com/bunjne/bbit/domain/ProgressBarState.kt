package com.bunjne.bbit.domain

sealed class ProgressBarState {

    data object Loading : ProgressBarState()

    data object Idle : ProgressBarState()
}