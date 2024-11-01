package com.bunjne.bbit.domain.usecase

import com.bunjne.bbit.data.datasource.AuthPreferencesDataSource

class SignOutUseCase(
    private val authPreferencesDataSource: AuthPreferencesDataSource
) {

    suspend operator fun invoke() {
        authPreferencesDataSource.clearAuthTokens()
    }
}