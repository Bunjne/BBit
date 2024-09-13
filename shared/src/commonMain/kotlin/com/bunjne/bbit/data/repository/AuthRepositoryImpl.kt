package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.data_source.AuthDataStore
import com.bunjne.bbit.data.remote.ApiConstants.ACCESS_TOKEN_GRANT_TYPE
import com.bunjne.bbit.data.remote.ApiConstants.REFRESH_TOKEN_GRANT_TYPE
import com.bunjne.bbit.data.remote.model.AuthDtoModel
import com.bunjne.bbit.data.remote.service.LoginService
import com.bunjne.bbit.domain.repository.AuthRepository
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AuthRepositoryImpl(
    private val api: LoginService,
    private val authDataStore: AuthDataStore
) : BaseRepository(), AuthRepository {

    override fun getAccessToken(): Flow<String?> = authDataStore.accessToken

    override fun getRefreshToken(): Flow<String?> = authDataStore.refreshToken

    override suspend fun signInWithClient(code: String): DataState<AuthDtoModel> = execute {
        api.getAccessToken(
            type = ACCESS_TOKEN_GRANT_TYPE,
            code = code
        ).also {
            authDataStore.saveAccessToken(it.accessToken)
            authDataStore.saveRefreshToken(it.refreshToken)
        }
    }

    override suspend fun refreshToken(req: HttpRequestBuilder.() -> Unit): DataState<AuthDtoModel> =
        execute {
            api.refreshToken(
                type = REFRESH_TOKEN_GRANT_TYPE,
                token = authDataStore.refreshToken.firstOrNull().orEmpty(),
                req = req
            ).also {
                authDataStore.saveAccessToken(it.accessToken)
                authDataStore.saveRefreshToken(it.refreshToken)
            }
        }
}