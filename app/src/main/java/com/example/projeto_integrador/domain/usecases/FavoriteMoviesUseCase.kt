package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.movies.Movie
import com.example.projeto_integrador.domain.repository.MoviesRepository

class GetFavoriteMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(): List<Movie> {
        return moviesRepository.getFavoriteMovies()
    }
}

class StoreFavoriteMovieUseCase(
    private val moviesRepository: MoviesRepository
){
    suspend operator fun invoke (movie: Movie) {
        moviesRepository.storeFavoriteMovie(movie)
    }
}

class DeleteFavoriteMovieUseCase(
    private val moviesRepository: MoviesRepository
){
    suspend operator fun invoke (movie: Movie) {
        moviesRepository.deleteFavoriteMovie(movie)
    }
}