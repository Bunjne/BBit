package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.Result.Error
import com.bunjne.bbit.data.Result.Success
import com.bunjne.bbit.data.model.DataError
import com.bunjne.bbit.data.util.catchNetworkError
import com.bunjne.bbit.data.util.handleNetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseRepository {

    protected suspend fun <Data> execute(
        fallback: suspend () -> Result<Data> = { Error(error = DataError.Network.INTERNAL) },
        onOnline: suspend () -> Data
    ): Result<Data> {
        return try {
            Success(onOnline())
        } catch (ex: Exception) {
            ex.handleNetworkError()
        }
    }

    protected fun <Data> networkFlow(
        fallback: suspend () -> Result<Data> = { Error(error = DataError.Network.INTERNAL) },
        onOnline: suspend () -> Data
    ): Flow<Result<Data>> = flow {
        emit(Success(onOnline()))
    }.catchNetworkError()
}
