package com.bunjne.bbit.data.model

import io.ktor.client.plugins.auth.providers.BearerTokens

data class AuthToken(
    val accessToken: String?,
    val refreshToken: String?
)

fun AuthToken.toBearerTokens(): BearerTokens? =
    if (refreshToken != null && accessToken != null) {
        BearerTokens(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    } else {
        null
    }


