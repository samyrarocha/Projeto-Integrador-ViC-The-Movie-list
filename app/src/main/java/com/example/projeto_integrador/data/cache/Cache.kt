package com.example.projeto_integrador.data.cache

import com.example.projeto_integrador.data.cache.model.CachedMovie

interface Cache {

    suspend fun getFavoriteMovies(): List<CachedMovie>

    suspend fun storeFavoriteMovie(movie: CachedMovie)

    suspend fun deleteFavorite(movie: CachedMovie)
}