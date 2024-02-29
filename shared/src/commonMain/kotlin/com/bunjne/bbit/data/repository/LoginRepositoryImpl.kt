package com.bunjne.bbit.data.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.remote.ApiEndpoints.AUTHORIZATION_CODE
import com.bunjne.bbit.data.remote.model.AuthDtoModel
import com.bunjne.bbit.data.remote.service.LoginService
import com.bunjne.bbit.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val api: LoginService
) : BaseRepository(), LoginRepository {

    override var token: String = ""

        override suspend fun signInWithClient(code: String): DataState<AuthDtoModel> = execute {
        api.getAccessToken(
            type = AUTHORIZATION_CODE,
            code = code
        ).also {
            token = it.accessToken
        }
    }
}