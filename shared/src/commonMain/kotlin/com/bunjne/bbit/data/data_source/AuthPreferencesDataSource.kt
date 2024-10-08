package com.bunjne.bbit.data.data_source

import com.bunjne.bbit.data.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthPreferencesDataSource {

    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun getAuthTokens(): AuthToken
}