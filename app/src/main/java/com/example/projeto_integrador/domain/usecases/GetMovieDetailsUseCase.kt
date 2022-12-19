package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.domain.repository.MoviesRepository

class GetMovieDetailsUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(
        movieId: Long
    ): MovieDetails {
        return moviesRepository.getMovieDetails(movieId)
    }
}