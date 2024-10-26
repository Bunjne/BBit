package com.bunjne.bbit.data.model

import com.bunjne.bbit.data.remote.error.ApiError

sealed interface DataError : Error {

    enum class Network : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        INTERNAL
    }

    enum class Local : DataError {
        DISK_FULL
    }

    data class NetworkError(val apiError: ApiError): DataError
}

sealed interface TestError : Error {

    enum class Network : TestError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        INTERNAL
    }
}