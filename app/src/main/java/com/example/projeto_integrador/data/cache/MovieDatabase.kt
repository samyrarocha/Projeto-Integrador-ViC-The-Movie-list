package com.example.projeto_integrador.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projeto_integrador.data.cache.daos.MoviesDao
import com.example.projeto_integrador.data.cache.model.CachedMovie

const val DATABASE_VERSION = 1

@Database(
    entities = [CachedMovie::class],
    version = DATABASE_VERSION
)

abstract class MovieDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}