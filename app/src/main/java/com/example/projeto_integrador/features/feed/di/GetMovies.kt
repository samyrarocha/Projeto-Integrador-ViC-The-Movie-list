package com.example.projeto_integrador.features.feed.di

import com.example.projeto_integrador.common.data.MoviesRepositoryImpl

class GetMovies (private val moviesRepositoryImpl: MoviesRepositoryImpl) {

    operator fun invoke() = moviesRepositoryImpl.getMovies()
        .filter { it.isNotEmpty() }
}