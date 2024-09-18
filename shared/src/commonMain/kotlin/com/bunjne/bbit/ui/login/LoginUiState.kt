package com.bunjne.bbit.ui.login

data class LoginUiState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)