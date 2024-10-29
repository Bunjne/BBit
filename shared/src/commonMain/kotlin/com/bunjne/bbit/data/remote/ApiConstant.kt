package com.bunjne.bbit.data.remote

import com.bunjne.bbit.BuildKonfig

const val TIME_SECOND_MS = 1000L

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