package com.bunjne.bbit.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDtoModel(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("scopes")
    val scopes: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String
)
