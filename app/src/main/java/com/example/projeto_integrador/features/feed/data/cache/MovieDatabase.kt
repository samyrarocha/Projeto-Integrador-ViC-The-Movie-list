package com.example.projeto_integrador.features.feed.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projeto_integrador.features.feed.data.cache.daos.MoviesDao
import com.example.projeto_integrador.features.feed.data.cache.model.CachedFavoriteMovies
import com.example.projeto_integrador.features.feed.data.cache.model.CachedMovie

@Database(
    entities = [
        (CachedMovie::class), (CachedFavoriteMovies::class)
    ],
    version = 1
)

abstract class MovieDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}