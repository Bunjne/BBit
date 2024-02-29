package com.bunjne.bbit.domain.repository

import com.bunjne.bbit.data.DataState
import com.bunjne.bbit.data.remote.model.AuthDtoModel

interface LoginRepository {
    var token: String

    suspend fun signInWithClient(code: String): DataState<AuthDtoModel>
}