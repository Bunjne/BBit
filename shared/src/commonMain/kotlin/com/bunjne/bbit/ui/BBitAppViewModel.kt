package com.bunjne.bbit.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunjne.bbit.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BBitAppViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState
        .onStart { loadRefreshToken() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppUiState()
        )

    private fun loadRefreshToken() {
        viewModelScope.launch {
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

@Immutable
data class AppUiState(
    var isLoading: Boolean = true,
    val refreshToken: String? = null
)