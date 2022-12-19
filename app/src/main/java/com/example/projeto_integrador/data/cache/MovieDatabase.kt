package com.example.projeto_integrador.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projeto_integrador.data.cache.daos.MoviesDao
import com.example.projeto_integrador.data.cache.model.CachedFavoriteMovies
import com.example.projeto_integrador.data.cache.model.CachedMovie

@Database(
    entities = [
        (CachedMovie::class), (CachedFavoriteMovies::class)
    ],
    version = 1
)

abstract class MovieDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}