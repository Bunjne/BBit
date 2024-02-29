package com.bunjne.bbit.data.remote.service

import com.bunjne.bbit.data.remote.ApiEndpoints
import com.bunjne.bbit.data.remote.model.AuthDtoModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.BasicAuthProvider
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.parameters

interface LoginService {
    suspend fun getAccessToken(
        type: String,
        code: String
    ): AuthDtoModel

    suspend fun refreshToken(
        type: String,
        token: String
    ): AuthDtoModel
}

class LoginServiceImpl(private val httpClient: HttpClient) : LoginService {

    override suspend fun getAccessToken(
        type: String,
        code: String
    ): AuthDtoModel =
        httpClient.post("${ApiEndpoints.AUTH_BASE_URL}access_token") {
            BasicAuthProvider(
                credentials = {
                    BasicAuthCredentials(
                        username = ApiEndpoints.BITBUCKET_CLIENT_ID,
                        password = ApiEndpoints.BITBUCKET_CLIENT_KEY
                    )
                }
            ).addRequestHeaders(this)

            setBody(FormDataContent(parameters {
                append("grant_type", type)
                append("code", code)
            }))
        }.body()

    override suspend fun refreshToken(type: String, token: String): AuthDtoModel {
        TODO("Not yet implemented")
    }
}