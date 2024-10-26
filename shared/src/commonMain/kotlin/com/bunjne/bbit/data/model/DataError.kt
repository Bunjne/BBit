package com.bunjne.bbit.data.model

sealed interface DataError : Error {

    enum class Network : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        INTERNAL,
    }

    enum class Local : DataError {
        DISK_FULL
    }
}

sealed interface TestError : Error {

    enum class Network : TestError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        INTERNAL
    }
}