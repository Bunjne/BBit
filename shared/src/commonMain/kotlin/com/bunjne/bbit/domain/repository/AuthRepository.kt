package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.remote.model.AuthDtoModel
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun signInWithClient(code: String): DataState<AuthDtoModel>

    suspend fun refreshToken(req: HttpRequestBuilder.() -> Unit): DataState<AuthDtoModel>
}