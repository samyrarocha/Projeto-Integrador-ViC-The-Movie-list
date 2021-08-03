package com.example.projeto_integrador.common.data.di

import com.example.projeto_integrador.common.data.api.interceptors.LoggingInterceptor
import com.example.projeto_integrador.common.data.api.interceptors.NetworkStatusInterceptor
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val ApiMovieModule = module {
    factory { HttpLoggingInterceptor() }
    factory { NetworkStatusInterceptor(get()) }
    factory { provideApi(get())}
    factory { provideOkHttpClient(get(), get()) }
    factory { provideRetrofit(get()) }
    factory { provideOkHttpLoggingInterceptor(get()) }

}

    fun provideApi (builder: Retrofit.Builder): TmdbApi {
        return builder
            .build()
            .create(TmdbApi::class.java)
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
    }

    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    fun provideOkHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }
