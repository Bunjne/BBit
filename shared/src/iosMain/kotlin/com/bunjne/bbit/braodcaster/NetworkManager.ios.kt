package com.bunjne.bbit.braodcaster

import kotlinx.coroutines.flow.Flow

class IOSNetworkManager : NetworkManager {

    override val networkStatusFlow: Flow<Boolean>
        get() = TODO("Not yet implemented")

    override suspend fun isNetworkAvailable(): Boolean {
        throw UnsupportedOperationException("Please implement the method")
    }

//    private var reachability: Reachability? = null

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}