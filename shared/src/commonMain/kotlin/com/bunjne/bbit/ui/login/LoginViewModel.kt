package com.bunjne.bbit.ui.login

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.domain.repository.AuthRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUIEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.OnLoginClicked -> {
                signIn(uiEvent.code)
            }
        }
    }

    private fun signIn(code: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(
                isLoading = true,
            )
            when (val loginResult = authRepository.signInWithClient(code)) {
                is DataState.Success -> {
                    _uiState.value = LoginUiState(
                        isLoading = false,
                        isSuccess = true,
                    )
                }

                is DataState.Error -> {
                    _uiState.value =
                        LoginUiState(
                            isLoading = false,
                            isError = true,
                            errorMessage = loginResult.message
                        )
                }
            }
        }
    }
}