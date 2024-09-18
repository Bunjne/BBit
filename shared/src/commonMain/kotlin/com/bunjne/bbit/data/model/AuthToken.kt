package com.bunjne.bbit.data.model

import io.ktor.client.plugins.auth.providers.BearerTokens

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)

fun AuthToken.toBearerTokens() = BearerTokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)

