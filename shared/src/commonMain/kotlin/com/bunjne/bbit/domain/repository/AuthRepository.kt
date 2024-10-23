package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.Result
import com.bunjne.bbit.data.remote.model.AuthDto
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun signInWithClient(code: String): Result<AuthDto>

    suspend fun refreshToken(req: HttpRequestBuilder.() -> Unit): Result<AuthDto>
}