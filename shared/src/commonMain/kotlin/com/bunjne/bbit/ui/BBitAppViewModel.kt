package com.bunjne.bbit.ui

import androidx.lifecycle.ViewModel
import com.bunjne.bbit.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class BBitAppViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun isLoggedIn(): Boolean = !runBlocking {
        authRepository.getRefreshToken().first()
    }.isNullOrEmpty()
}