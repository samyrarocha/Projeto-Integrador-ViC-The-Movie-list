package com.example.projeto_integrador.features.feed.data.cache

import com.example.projeto_integrador.features.feed.data.cache.model.CachedMovie

interface Cache {

    suspend fun getFavoriteMovies(): List<CachedMovie>

    suspend fun storeFavoriteMovie(movie: CachedMovie)

    suspend fun deleteFavorite(movie: CachedMovie)
}