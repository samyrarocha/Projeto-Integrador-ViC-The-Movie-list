package com.example.projeto_integrador.data.cache

import com.example.projeto_integrador.data.cache.daos.MoviesDao
import com.example.projeto_integrador.data.cache.model.CachedMovie

class RoomCache(
    private val moviesDao: MoviesDao
): Cache {

    override suspend fun getFavoriteMovies(): List<CachedMovie> {
        return moviesDao.getMovies()
    }

    override suspend fun storeFavoriteMovie(movie: CachedMovie) {
        moviesDao.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavorite(movie: CachedMovie) {
        moviesDao.deleteMovie(movie)
    }
}