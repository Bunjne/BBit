package com.bunjne.bbit.data

import com.bunjne.bbit.data.model.Error

typealias RootError = Error

sealed interface Result<out Data> {

    data class Success<out Data>(val data: Data) :
        Result<Data>

    data class Error<out Data>(
        val error: RootError,
        val exception: Throwable? = null
    ) : Result<Data>
}