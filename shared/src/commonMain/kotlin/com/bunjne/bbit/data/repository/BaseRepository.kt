package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.DataState.Error
import com.bunjne.bbit.data.DataState.Success
import com.bunjne.bbit.data.remote.StatusCode.INTERNAL_ERROR
import com.bunjne.bbit.data.remote.StatusCode.NO_INTERNET_ERROR
import com.bunjne.bbit.data.remote.StatusCode.REQUEST_TIMEOUT
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.utils.io.errors.IOException


open class BaseRepository {

    protected suspend fun <Data> execute(
        fallback: suspend () -> DataState<Data> = { Error(NO_INTERNET_ERROR) },
        onOnline: suspend () -> Data
    ): DataState<Data> {
        return try {
            Success(onOnline())
//        } catch (apiException: ApiException) {
//            Error(
//                statusCode = apiException.error?.code ?: INTERNAL_ERROR,
//                message = apiException.error?.message
//            )
        } catch (timeoutException: HttpRequestTimeoutException) {
            Napier.e(timeoutException.message.toString())
            Error(statusCode = REQUEST_TIMEOUT, message = "Please check you network connection")
        } catch (ioException: IOException) {
            Napier.e(ioException.message.toString())
            Error(statusCode = NO_INTERNET_ERROR, message = ioException.message)
        } catch (ex: Exception) {
            Napier.e(ex.message.toString())
            Error(statusCode = INTERNAL_ERROR, message = ex.message)
        }
    }
}
