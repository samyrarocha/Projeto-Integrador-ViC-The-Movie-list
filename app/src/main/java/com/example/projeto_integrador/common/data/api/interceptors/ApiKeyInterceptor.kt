package com.example.projeto_integrador.common.data.api.interceptors

import com.example.projeto_integrador.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val originalHttpUrl = originalRequest.url

        val httpUrlBuilder = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(httpUrlBuilder)

        val request = requestBuilder.build()

        return chain.proceed(request)
    }

}