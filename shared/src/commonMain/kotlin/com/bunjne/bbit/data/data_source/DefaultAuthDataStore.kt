package com.bunjne.bbit.data.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bunjne.bbit.data.model.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

class DefaultAuthDataStore(
    private val dataStore: DataStore<Preferences>
) : AuthDataStore {

    override val accessToken: Flow<String?> = dataStore.data.map {
        it[ACCESS_TOKEN_KEY]
    }

    override val refreshToken: Flow<String?> = dataStore.data.map {
        it[REFRESH_TOKEN_KEY]
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit {
            it[REFRESH_TOKEN_KEY] = token
        }
    }

    override suspend fun getAuthTokens(): AuthToken = AuthToken(
        accessToken = accessToken.firstOrNull().orEmpty(),
        refreshToken = refreshToken.firstOrNull().orEmpty()
    )
}