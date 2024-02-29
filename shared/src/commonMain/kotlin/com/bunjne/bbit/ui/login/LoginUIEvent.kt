package com.bunjne.bbit.ui.login

sealed class LoginUIEvent {
    data class OnLoginClicked(val code: String): LoginUIEvent()
}
