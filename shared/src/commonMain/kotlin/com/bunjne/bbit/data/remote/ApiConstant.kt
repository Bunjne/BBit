package com.bunjne.bbit.data.remote

const val TIME_SECOND_MS = 1000L

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
}

object ApiConstants {
    const val API_TIMEOUT_MS = TIME_SECOND_MS * 10
    const val ACCESS_TOKEN_GRANT_TYPE = "authorization_code"
    const val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
    const val BITBUCKET_CLIENT_ID = "***REMOVED***"
    const val BITBUCKET_CLIENT_KEY = "***REMOVED***"
}

object ApiEndpoints {
    const val SPACEX_URL = "https://api.spacexdata.com/v5/"
    const val BASE_URL = "https://api.bitbucket.org/2.0/"
    const val AUTH_BASE_URL = "https://bitbucket.org/site/oauth2/"
}