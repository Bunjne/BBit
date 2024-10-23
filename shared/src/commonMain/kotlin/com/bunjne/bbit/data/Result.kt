package com.bunjne.bbit.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

sealed interface Result<out Data> {

    data class Success<out Data>(val data: Data) :
        Result<Data>

    data class Error(
        val statusCode: Int? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>

    data object Loading : Result<Nothing>
}

fun <T> Flow<Result<T>>.asResult(): Flow<Result<T>> = onStart { emit(Result.Loading) }
    .catch { emit(Result.Error(exception = it)) }

//fun <T> Flow<T>.asResult(): Flow<Result<T>> = map<T, Result<T>> { Result.Success(it) }
//    .onStart { emit(Result.Loading) }
//    .catch { emit(Result.Error(exception = it)) }