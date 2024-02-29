package com.bunjne.bbit.data

sealed class DataState<out Data> {

    data class Success<out Data>(val data: Data) : DataState<Data>()

    data class Error(val statusCode: Int? = null, val message: String? = null) : DataState<Nothing>()
}