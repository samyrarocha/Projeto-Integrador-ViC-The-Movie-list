package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.movies.Discover
import com.example.projeto_integrador.domain.repository.MoviesRepository


class RequestNextPageOfMoviesUseCase (
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(
        pageToLoad: Int,
        genreId: String?
    ): Discover {
        val movies =  moviesRepository.requestMovies(pageToLoad, genreId)
        moviesRepository.storeMovieList(movies.movies)
        return movies
    }
}