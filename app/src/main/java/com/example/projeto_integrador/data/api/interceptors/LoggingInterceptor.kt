package com.example.projeto_integrador.data.api.interceptors

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.i(null, message)
    }
}