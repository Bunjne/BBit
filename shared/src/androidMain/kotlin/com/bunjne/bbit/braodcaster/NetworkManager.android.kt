package com.bunjne.bbit.braodcaster

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first

class AndroidNetworkManager(private val context: Context) : NetworkManager {

    private val _networkStatusFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val networkStatusFlow: SharedFlow<Boolean> = _networkStatusFlow.asSharedFlow()

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Napier.d("Network connection is available")
            _networkStatusFlow.tryEmit(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Napier.d("Network connection is unavailable")
            _networkStatusFlow.tryEmit(false)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val isConnected =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)

            _networkStatusFlow.tryEmit(isConnected)
        }
    }

    @SuppressLint("MissingPermission")
    override fun start() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        _networkStatusFlow.tryEmit(hasActiveNetwork())
    }

    override fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override suspend fun isNetworkAvailable(): Boolean = networkStatusFlow.first()

    @SuppressLint("MissingPermission")
    private fun hasActiveNetwork() = connectivityManager.activeNetwork != null
}