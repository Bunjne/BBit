package com.bunjne.bbit.usecase

import com.bunjne.bbit.data.remote.error.ApiException

sealed class ViewState<out Data> {

    data class Success<out Data>(val data: Data? = null) : ViewState<Data>()

    data object Loading : ViewState<Nothing>()

    data class Error(val statusCode: Int, val message: String? = null) : ViewState<Nothing>()
}