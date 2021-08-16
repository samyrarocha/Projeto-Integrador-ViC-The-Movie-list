package com.example.projeto_integrador.common.data.di

import com.example.projeto_integrador.common.data.api.interceptors.ApiKeyInterceptor
import com.example.projeto_integrador.common.data.api.interceptors.LoggingInterceptor
import com.example.projeto_integrador.common.data.api.interceptors.NetworkStatusInterceptor
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.data.api.models.ConnectionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val ApiMovieModule = module {
    single { provideOkHttpLoggingInterceptor(LoggingInterceptor())}//HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT) }
    single { ConnectionManager(context = androidContext()) }
    single { NetworkStatusInterceptor(
        connectionManager = ConnectionManager(
            context = androidContext()
        )
    )
    }
    single {
        provideOkHttpClient(
            httpLoggingInterceptor = get(),
            networkStatusInterceptor = get(),
            apiKeyInterceptor = ApiKeyInterceptor()
        )
    }
    single { provideRetrofit(okHttpClient = get()) }

}

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    fun provideOkHttpLoggingInterceptor(
        loggingInterceptor: LoggingInterceptor
    ) : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

class ApiService(private val retrofit: Retrofit) {
    fun <T> createService(service: Class<T>) : T {
        return retrofit.create(service)
    }
}

internal val apiDependencies by lazy {
    loadKoinModules(ApiMovieModule)
}

fun initApiMoviesDependencies() = apiDependencies

