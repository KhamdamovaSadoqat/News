package com.example.news.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class InternetChecker(
    private val context: Context,
    private val onNetworkAvailableCallbacks: OnNetworkAvailableCallbacks
) : ConnectivityManager.NetworkCallback() {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()!!

    // return`true` when device is connected to network else `false`
    fun hasNetworkConnection() = (connectivityManager.activeNetworkInfo != null)

    fun enable() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun disable() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        onNetworkAvailableCallbacks.onPositive()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        onNetworkAvailableCallbacks.onNegative()
    }

    // Callbacks to handle the network status changes
    interface OnNetworkAvailableCallbacks {
        fun onPositive()
        fun onNegative()
    }
}