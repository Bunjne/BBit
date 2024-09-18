package com.bunjne.bbit.ui.login

sealed class LoginUiEvent {
    data class OnLoginClicked(val code: String): LoginUiEvent()
}
