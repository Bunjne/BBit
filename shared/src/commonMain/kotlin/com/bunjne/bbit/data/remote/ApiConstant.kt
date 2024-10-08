package com.bunjne.bbit.data.remote

import com.bunjne.bbit.BuildKonfig

const val TIME_SECOND_MS = 1000L

sealed interface Error
sealed interface NetworkError : Error {
    data object ConnectionTimeout : NetworkError
    data object NoInternetConnection : NetworkError
    data object ServerError : NetworkError
    data object GeneralError : NetworkError
}

object StatusCode {
    const val SUCCESS = 200
    const val RESOURCE_NOT_FOUND = 404
    const val INTERNAL_ERROR = 105
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED_ACCESS = 401
    const val NO_INTERNET_ERROR = 104
    const val EMPTY_RESPONSE = 204
    const val RESET_CONTENT = 205
    const val SERVER_ERROR = 500
    const val CACHE_NOT_FOUND = 504
    const val REQUEST_TIMEOUT = 408
}

object ApiConstants {
    const val API_TIMEOUT_MS = TIME_SECOND_MS * 10
    const val ACCESS_TOKEN_GRANT_TYPE = "authorization_code"
    const val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
    val BITBUCKET_CLIENT_ID = BuildKonfig.BITBUCKET_CLIENT_ID
    val BITBUCKET_CLIENT_KEY = BuildKonfig.BITBUCKET_CLIENT_KEY
    val BITBUCKET_AUTH_CALLBACK_URL = BuildKonfig.BITBUCKET_AUTH_CALLBACK_URL
}

object ApiEndpoints {
    val SPACEX_URL = BuildKonfig.SPACEX_URL
    val BASE_URL = BuildKonfig.BASE_URL
    val AUTH_BASE_URL = BuildKonfig.AUTH_BASE_URL
}