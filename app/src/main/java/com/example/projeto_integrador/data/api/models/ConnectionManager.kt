package com.example.projeto_integrador.data.api.models

import android.content.Context
import android.net.*

class ConnectionManager constructor( private val context: Context) {

    fun isConnected(): Boolean{
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetwork ?: return false
        val actNw = manager.getNetworkCapabilities(networkInfo) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}