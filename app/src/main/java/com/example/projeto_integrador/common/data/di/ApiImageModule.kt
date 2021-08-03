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


val ApiImageModule = module {
    factory { HttpLoggingInterceptor() }
    factory { NetworkStatusInterceptor(get()) }
    factory { provideImageOkHttpClient(get(), get()) }
    factory { provideImageRetrofit(get()) }
    factory { provideImageOkHttpLoggingInterceptor(get()) }
}

    fun provideImageMovieApi (builder: Retrofit.Builder): TmdbApi {
        return builder
            .build()
            .create(TmdbApi::class.java)
    }

    fun provideImageRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_IMAGE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
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


    fun provideImageOkHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor

    }
