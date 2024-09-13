package com.bunjne.bbit

import com.bunjne.bbit.domain.repository.AuthRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BBitAppViewModel(
    authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            authRepository.getRefreshToken().collectLatest { refreshToken ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        refreshToken = refreshToken
                    )
                }
            }
        }
    }
}

data class AppUiState(
    var isLoading: Boolean = false,
    val refreshToken: String? = null
)