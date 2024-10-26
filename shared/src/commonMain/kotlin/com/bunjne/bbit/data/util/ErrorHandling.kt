package com.bunjne.bbit.data.util

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.Result.Error
import com.bunjne.bbit.data.model.DataError
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException

fun <Data> Flow<Result<Data>>.catchNetworkError(): Flow<Result<Data>> =
    catch {
        emit(it.handleNetworkError())
    }

// Example of network error handling
// Issue: https://github.com/ktorio/ktor/issues/1165
fun <Data> Throwable.handleNetworkError(): Result<Data> {
    Napier.e("Network Error: ${message.toString()}")
    val error = when (this) {
        // 400..499, 500..599
        is ClientRequestException, is ServerResponseException -> DataError.Network.INTERNAL
        is HttpRequestTimeoutException -> DataError.Network.REQUEST_TIMEOUT
        is IOException -> DataError.Network.SERVER_ERROR
        is SerializationException -> DataError.Network.SERIALIZATION
        else -> throw this // Depend on error logic for each app
    }

    return Error(error = error, exception = this)
}