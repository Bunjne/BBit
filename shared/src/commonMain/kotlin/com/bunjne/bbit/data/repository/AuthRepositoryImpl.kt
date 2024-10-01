package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.data_source.AuthPreferencesDataSource
import com.bunjne.bbit.data.remote.ApiConstants.ACCESS_TOKEN_GRANT_TYPE
import com.bunjne.bbit.data.remote.ApiConstants.REFRESH_TOKEN_GRANT_TYPE
import com.bunjne.bbit.data.remote.model.AuthDto
import com.bunjne.bbit.data.remote.service.LoginService
import com.bunjne.bbit.domain.repository.AuthRepository
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AuthRepositoryImpl(
    private val api: LoginService,
    private val authPreferencesDataSource: AuthPreferencesDataSource
) : BaseRepository(), AuthRepository {

    override fun getAccessToken(): Flow<String?> = authPreferencesDataSource.accessToken

    override fun getRefreshToken(): Flow<String?> = authPreferencesDataSource.refreshToken

    override suspend fun signInWithClient(code: String): DataState<AuthDto> = execute {
        api.getAccessToken(
            type = ACCESS_TOKEN_GRANT_TYPE,
            code = code
        ).also {
            authPreferencesDataSource.saveAccessToken(it.accessToken)
            authPreferencesDataSource.saveRefreshToken(it.refreshToken)
        }
    }

    override suspend fun refreshToken(req: HttpRequestBuilder.() -> Unit): DataState<AuthDto> =
        execute {
            api.refreshToken(
                type = REFRESH_TOKEN_GRANT_TYPE,
                token = authPreferencesDataSource.refreshToken.firstOrNull().orEmpty(),
                req = req
            ).also {
                authPreferencesDataSource.saveAccessToken(it.accessToken)
                authPreferencesDataSource.saveRefreshToken(it.refreshToken)
            }
        }
}