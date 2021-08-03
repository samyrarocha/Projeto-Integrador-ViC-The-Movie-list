package com.example.projeto_integrador.common.data.api.interceptors

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.i(null, message)
    }
}