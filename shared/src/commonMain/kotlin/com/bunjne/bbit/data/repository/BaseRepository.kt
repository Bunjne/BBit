package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.DataState.Success
import com.bunjne.bbit.data.DataState.Error
import com.bunjne.bbit.data.remote.StatusCode.INTERNAL_ERROR
import com.bunjne.bbit.data.remote.StatusCode.NO_INTERNET_ERROR
import com.bunjne.bbit.data.remote.error.ApiException
import io.ktor.utils.io.errors.IOException


open class BaseRepository {

    protected suspend fun <Data> execute(
        fallback: suspend () -> DataState<Data> = { Error(NO_INTERNET_ERROR) },
        onOnline: suspend () -> Data
    ): DataState<Data> {
        return try {
            Success(onOnline())
            /*if (true) { //TODO networkStateManager.isOnline()
                DataState.Success(onOnline())
            } else {
                fallback()
            }*/
        } catch (apiException: ApiException) {
            Error(
                statusCode = apiException.error?.code ?: INTERNAL_ERROR,
                message = apiException.error?.message
            )
        } catch (timeoutException: IOException) {
            Error(NO_INTERNET_ERROR)
        } catch (ex: Exception) {
            Error(INTERNAL_ERROR)
        }
    }
}
