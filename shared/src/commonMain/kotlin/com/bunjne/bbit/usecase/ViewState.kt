package com.bunjne.bbit.usecase

sealed class ViewState<out Data> {

    data class Success<Data>(val data: Data? = null) : ViewState<Data>()

    data object Loading : ViewState<Nothing>()

    data class Error<Error>(val error: Error) : ViewState<Nothing>()
}