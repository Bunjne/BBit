package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.Result.Error
import com.bunjne.bbit.data.Result.Success
import com.bunjne.bbit.data.model.DataError
import com.bunjne.bbit.data.util.catchNetworkError
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseRepository {

    protected suspend fun <Data> execute(
        fallback: suspend () -> Result<Data> = { Error(error = DataError.Network.INTERNAL) },
        onOnline: suspend () -> Data
    ): Result<Data> {
        return try {
            Success(onOnline())
        } catch (timeoutException: HttpRequestTimeoutException) {
            Napier.e(timeoutException.message.toString())
            Error(error = DataError.Network.REQUEST_TIMEOUT, exception = timeoutException)
        } catch (ioException: IOException) {
            Napier.e(ioException.message.toString())
            Error(error = DataError.Network.SERVER_ERROR, exception = ioException)
        } catch (ex: Exception) {
            Napier.e(ex.message.toString())
            Error(error = DataError.Network.INTERNAL, exception = ex)
        }
    }

    protected fun <Data> networkFlow(
        fallback: suspend () -> Result<Data> = { Error(error = DataError.Network.INTERNAL) },
        onOnline: suspend () -> Data
    ): Flow<Result<Data>> = flow {
        emit(Success(onOnline()))
    }.catchNetworkError()
}
