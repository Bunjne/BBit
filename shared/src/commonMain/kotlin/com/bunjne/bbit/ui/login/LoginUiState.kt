package com.bunjne.bbit.ui.login

import androidx.compose.runtime.Immutable
import com.bunjne.bbit.ui.model.UiText


@Immutable
data class LoginUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: UiText? = null
)