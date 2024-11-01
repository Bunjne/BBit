package com.bunjne.bbit.ui.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunjne.bbit.domain.usecase.SignOutUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MoreViewModel(
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    private val _uiEvent by lazy { Channel<MoreUiEvent>() }
    val uiEvent: Flow<MoreUiEvent> by lazy { _uiEvent.receiveAsFlow() }

    fun onAction(event: MoreUiAction) {
        when (event) {
            is MoreUiAction.onSignOut -> singOut()
        }
    }

    private fun singOut() {
        viewModelScope.launch {
            signOutUseCase.invoke()
            _uiEvent.send(MoreUiEvent.SingOutSuccessFully)
        }
    }
}