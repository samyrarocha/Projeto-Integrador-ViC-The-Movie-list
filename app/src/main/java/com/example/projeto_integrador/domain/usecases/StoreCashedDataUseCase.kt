package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.movies.Movie
import com.example.projeto_integrador.domain.repository.MoviesRepository

class StoreCashedDataUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke (movies: List<Movie>) {
        moviesRepository.storeMovieList(movies)
    }
}