package com.example.projeto_integrador.data.cache

import com.example.projeto_integrador.data.cache.daos.MoviesDao
import com.example.projeto_integrador.data.cache.model.CachedMovie

class RoomCache(
    private val moviesDao: MoviesDao
): Cache {

    override suspend fun getMovies(): List<CachedMovie> {
        return moviesDao.getMovies()
    }

    override suspend fun getFavoriteMovies(): List<CachedMovie> {
        return moviesDao.getFavoriteMovies()
    }

    override suspend fun storeNewCachedData(movie: List<CachedMovie>) {
        moviesDao.storeNewCacheData(movie)
    }

    override suspend fun updateFavoriteMovie(movie: CachedMovie) {
        moviesDao.updateFavoriteMovie(movie)
    }
}