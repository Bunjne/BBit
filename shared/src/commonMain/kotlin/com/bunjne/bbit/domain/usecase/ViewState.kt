package com.bunjne.bbit.domain.usecase

import com.bunjne.bbit.domain.ProgressBarState

sealed class ViewState<out Data> {

    data class Success<out Data>(val data: Data? = null) : ViewState<Data>()

    data class Loading(val state: ProgressBarState = ProgressBarState.Loading) : ViewState<Nothing>()

    data class Error(val statusCode: Int, val message: String? = null) : ViewState<Nothing>()
}