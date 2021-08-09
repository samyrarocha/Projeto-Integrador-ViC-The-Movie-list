package com.example.projeto_integrador.common.data.di

import com.example.projeto_integrador.common.data.api.interceptors.LoggingInterceptor
import com.example.projeto_integrador.common.data.api.interceptors.NetworkStatusInterceptor
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.data.api.models.ConnectionManager
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.features.feed.di.allMoviesModule
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val ApiMovieModule = module {
    single { HttpLoggingInterceptor() }
    single { ConnectionManager(context = androidContext()) }
    factory<Interceptor> { NetworkStatusInterceptor(connectionManager = get()) }
    factory { ApiService(retrofit = get()).createService(TmdbApi::class.java)}
    single {
        provideImageOkHttpClient(
            httpLoggingInterceptor = get(),
            networkStatusInterceptor = get()
        )
    }
    single { provideImageRetrofit(okHttpClient = get()) }

}

    fun provideImageRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    fun provideImageOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(httpLoggingInterceptor)
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

