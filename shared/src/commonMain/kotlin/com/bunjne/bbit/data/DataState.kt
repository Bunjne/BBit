package com.bunjne.bbit.data

sealed class DataState<out Data> {

    data class Success<out Data>(val data: Data? = null) : DataState<Data>()

    data class Error(val statusCode: Int, val message: String? = null) : DataState<Nothing>()
}