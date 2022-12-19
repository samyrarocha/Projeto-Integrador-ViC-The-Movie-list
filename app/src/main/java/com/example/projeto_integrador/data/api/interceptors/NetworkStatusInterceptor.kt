package com.example.projeto_integrador.data.api.interceptors

import com.example.projeto_integrador.data.api.models.ConnectionManager
import com.example.projeto_integrador.domain.model.NetworkUnavailableException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkStatusInterceptor constructor( private val connectionManager: ConnectionManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}