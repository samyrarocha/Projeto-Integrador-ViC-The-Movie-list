package com.example.projeto_integrador.data.cache

import com.example.projeto_integrador.data.cache.model.CachedMovie

interface Cache {

    suspend fun getMovies(): List<CachedMovie>

    suspend fun getFavoriteMovies(): List<CachedMovie>

    suspend fun searchMovies(query: String): List<CachedMovie>

    suspend fun storeNewCachedData(movie: List<CachedMovie>)

    suspend fun updateFavoriteMovie(movie: CachedMovie)
}