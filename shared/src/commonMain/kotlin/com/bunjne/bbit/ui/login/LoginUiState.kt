package com.bunjne.bbit.ui.login

import androidx.compose.runtime.Immutable


@Immutable
data class LoginUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)