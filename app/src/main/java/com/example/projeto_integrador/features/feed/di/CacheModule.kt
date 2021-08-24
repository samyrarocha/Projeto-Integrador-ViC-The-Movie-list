package com.example.projeto_integrador.features.feed.di

import android.content.Context
import androidx.room.Room
import com.example.projeto_integrador.features.feed.data.cache.Cache
import com.example.projeto_integrador.features.feed.data.cache.MovieDatabase
import com.example.projeto_integrador.features.feed.data.cache.RoomCache
import com.example.projeto_integrador.features.feed.data.cache.daos.MoviesDao
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val CacheModule = module {

    single { provideDatabase(context = androidContext()) }
    single { bindCache(cache = get()) }
    single { provideMoviesDao(movieDatase = get()) }

    factory { RoomCache(moviesDao = get()) }


}

fun bindCache(cache: RoomCache): Cache = cache

fun provideDatabase(context: Context): MovieDatabase{
    return Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "movie.db"
    )
        .build()
}

fun provideMoviesDao(
    movieDatase: MovieDatabase
): MoviesDao = movieDatase.moviesDao()

internal val cacheDependencies by lazy {
    loadKoinModules(CacheModule)
}

fun initCacheDependencies() = cacheDependencies