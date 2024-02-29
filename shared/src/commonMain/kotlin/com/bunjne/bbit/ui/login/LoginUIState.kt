package com.bunjne.bbit.ui.login

import com.bunjne.bbit.data.remote.model.AuthDtoModel
import com.bunjne.bbit.domain.ProgressBarState
import com.bunjne.bbit.domain.usecase.ViewState

data class LoginUIState(
    val loginState: ViewState<AuthDtoModel> = ViewState.Loading(ProgressBarState.Loading),
)