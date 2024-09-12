package com.bunjne.bbit.data.remote.service

import com.bunjne.bbit.data.remote.ApiConstants
import com.bunjne.bbit.data.remote.model.AuthDtoModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.BasicAuthProvider
import io.ktor.client.request.HttpRequestBuilder
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
        token: String,
        req: HttpRequestBuilder.() -> Unit
    ): AuthDtoModel
}

class LoginServiceImpl(
    private val httpClient: HttpClient
) : LoginService {

    override suspend fun getAccessToken(
        type: String,
        code: String
    ): AuthDtoModel =
        httpClient.post("access_token") {
            BasicAuthProvider(
                credentials = {
                    BasicAuthCredentials(
                        username = ApiConstants.BITBUCKET_CLIENT_ID,
                        password = ApiConstants.BITBUCKET_CLIENT_KEY
                    )
                }
            ).addRequestHeaders(this)

            setBody(FormDataContent(parameters {
                append("grant_type", type)
                append("code", code)
            }))
        }.body()

    override suspend fun refreshToken(
        type: String,
        token: String,
        req: HttpRequestBuilder.() -> Unit
    ): AuthDtoModel =
        httpClient.post("access_token") {
            BasicAuthProvider(
                credentials = {
                    BasicAuthCredentials(
                        username = ApiConstants.BITBUCKET_CLIENT_ID,
                        password = ApiConstants.BITBUCKET_CLIENT_KEY
                    )
                }
            ).addRequestHeaders(this)
            req()
            setBody(
                FormDataContent(
                    parameters {
                        append("grant_type", type)
                        append("refresh_token", token)
                    }
                )
            )
        }.body()
}