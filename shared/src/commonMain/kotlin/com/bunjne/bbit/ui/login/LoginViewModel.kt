package com.bunjne.bbit.ui.login

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.domain.repository.LoginRepository
import com.bunjne.bbit.domain.usecase.ViewState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    fun onUIEvent(uiEvent: LoginUIEvent) {
        when (uiEvent) {
            is LoginUIEvent.OnLoginClicked -> {
                signIn(uiEvent.code)
            }
        }
    }

    private fun signIn(code: String) {
        viewModelScope.launch {
            when (val loginResult = loginRepository.signInWithClient(code)) {
                is DataState.Success -> {
                    _uiState.value = LoginUIState(
                        loginState = ViewState.Success(loginResult.data)
                    )
                }

                is DataState.Error -> {
                    _uiState.value =
                        LoginUIState(ViewState.Error(loginResult.statusCode ?: 0, loginResult.message))
                }
            }
        }
    }
}