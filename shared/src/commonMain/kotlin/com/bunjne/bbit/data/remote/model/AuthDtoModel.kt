package com.bunjne.bbit.data.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class AuthDtoModel(
    @JsonNames("access_token")
    val accessToken: String,
    @JsonNames("scopes")
    val scopes: String,
    @JsonNames("token_type")
    val tokenType: String,
    @JsonNames("expires_in")
    val expiresIn: Long,
    @JsonNames("state")
    val state: String,
    @JsonNames("refresh_token")
    val refreshToken: String
)
