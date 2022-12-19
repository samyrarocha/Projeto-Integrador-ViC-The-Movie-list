package com.example.projeto_integrador.di

import android.content.Context
import androidx.room.Room
import com.example.projeto_integrador.data.cache.Cache
import com.example.projeto_integrador.data.cache.MovieDatabase
import com.example.projeto_integrador.data.cache.RoomCache
import com.example.projeto_integrador.data.cache.daos.MoviesDao
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val CacheModule = module {

    single { provideDatabase(context = androidContext()) }
    single { bindCache(cache = get()) }
    single { provideMoviesDao(movieDatabase = get()) }

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
    movieDatabase: MovieDatabase
): MoviesDao = movieDatabase.moviesDao()

internal val cacheDependencies by lazy {
    loadKoinModules(CacheModule)
}

fun initCacheDependencies() = cacheDependencies