package com.example.projeto_integrador.features.moviedetails.usecase

import com.example.projeto_integrador.common.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository

class GetMovieDetailsUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(
        movieId: Long
    ): MovieDetails {
        return moviesRepository.getMovieDetails(movieId)
    }
}