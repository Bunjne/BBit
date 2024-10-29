package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.Result.Error
import com.bunjne.bbit.data.Result.Success
import com.bunjne.bbit.data.model.DataError
import com.bunjne.bbit.data.util.catchNetworkError
import com.bunjne.bbit.data.util.handleNetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.bunjne.bbit.braodcaster.NetworkManager

open class BaseRepository(
    private val networkManager: NetworkManager
) {

    protected suspend fun <Data> execute(
        fallback: suspend () -> Result<Data> = { Error(error = DataError.Network.INTERNAL) },
        onOnline: suspend () -> Data
    ): Result<Data> {
        return try {
            val result = if (networkManager.isNetworkAvailable()) {
                Success(onOnline())
            } else {
                Error(error = DataError.Network.NO_INTERNET)
            }
            result
        } catch (ex: Exception) {
            ex.handleNetworkError()
        }
    }

    protected fun <Data> networkFlow(
        fallback: suspend () -> Result<Data> = { Error(error = DataError.Network.INTERNAL) },
        onOnline: suspend () -> Data
    ): Flow<Result<Data>> = flow {
        val result = if (networkManager.isNetworkAvailable()) {
            Success(onOnline())
        } else {
            Error(error = DataError.Network.NO_INTERNET)
        }
        emit(result)
    }.catchNetworkError()
}
