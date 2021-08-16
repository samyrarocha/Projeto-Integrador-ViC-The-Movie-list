package com.example.projeto_integrador.features.feed.usecases

import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository


class RequestNextPageOfMoviesUseCase (
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(
        pageToLoad: Int,
        genreId: String?
    ): Discover {
        return moviesRepository.requestMoreMovies(pageToLoad, genreId)
    }
}