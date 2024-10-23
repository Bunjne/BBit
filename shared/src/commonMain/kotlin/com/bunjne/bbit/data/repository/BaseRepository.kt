package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.Result.Error
import com.bunjne.bbit.data.Result.Success
import com.bunjne.bbit.data.remote.StatusCode.INTERNAL_ERROR
import com.bunjne.bbit.data.remote.StatusCode.NO_INTERNET_ERROR
import com.bunjne.bbit.data.remote.StatusCode.REQUEST_TIMEOUT
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.utils.io.errors.IOException


open class BaseRepository {

    protected suspend fun <Data> execute(
        fallback: suspend () -> Result<Data> = { Error(NO_INTERNET_ERROR) },
        onOnline: suspend () -> Data
    ): Result<Data> {
        return try {
            Success(onOnline())
//        } catch (apiException: ApiException) {
//            Error(
//                statusCode = apiException.error?.code ?: INTERNAL_ERROR,
//                message = apiException.error?.message
//            )
        } catch (timeoutException: HttpRequestTimeoutException) {
            Napier.e(timeoutException.message.toString())
            Error(statusCode = REQUEST_TIMEOUT, exception = timeoutException)
        } catch (ioException: IOException) {
            Napier.e(ioException.message.toString())
            Error(statusCode = NO_INTERNET_ERROR, exception = ioException)
        } catch (ex: Exception) {
            Napier.e(ex.message.toString())
            Error(statusCode = INTERNAL_ERROR, exception = ex)
        }
    }
}
