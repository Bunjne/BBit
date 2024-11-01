package com.bunjne.bbit.braodcaster

import kotlinx.coroutines.flow.Flow

interface NetworkManager {
    val networkStatusFlow: Flow<Boolean>
    suspend fun isNetworkAvailable(): Boolean
    fun start()
    fun stop()
}
