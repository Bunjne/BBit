package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.data_source.AuthDataStore
import com.bunjne.bbit.data.remote.ApiConstants.ACCESS_TOKEN_GRANT_TYPE
import com.bunjne.bbit.data.remote.ApiConstants.REFRESH_TOKEN_GRANT_TYPE
import com.bunjne.bbit.data.remote.model.AuthDtoModel
import com.bunjne.bbit.data.remote.service.LoginService
import com.bunjne.bbit.domain.repository.LoginRepository
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.firstOrNull

class LoginRepositoryImpl(
    private val api: LoginService,
    private val authDataStore: AuthDataStore
) : BaseRepository(), LoginRepository {

    override suspend fun getAccessToken(): String? = authDataStore.accessToken.firstOrNull()

    override suspend fun getRefreshToken(): String? = authDataStore.refreshToken.firstOrNull()

    override suspend fun signInWithClient(code: String): DataState<AuthDtoModel> = execute {
        api.getAccessToken(
            type = ACCESS_TOKEN_GRANT_TYPE,
            code = code
        ).also {
            authDataStore.saveAccessToken(it.accessToken)
            authDataStore.saveRefreshToken(it.refreshToken)
        }
    }

    override suspend fun refreshToken(req: HttpRequestBuilder.() -> Unit): DataState<AuthDtoModel> = execute {
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