package com.example.projeto_integrador.domain.repository

import com.example.projeto_integrador.domain.model.movies.Discover
import com.example.projeto_integrador.domain.model.movies.Movie
import com.example.projeto_integrador.domain.model.movies.Search
import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.presentation.feed.models.SearchParameters


interface MoviesRepository {

    suspend fun requestMovies (pageToLoad: Int, genreFilter: String?): Discover

    suspend fun searchMovies (pageToLoad: Int, searchParameters: SearchParameters): Search

    suspend fun getMovieDetails(movieId: Long): MovieDetails

    suspend fun getMovies(): List<Movie>

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun storeMovieList(movie: List<Movie>)

    suspend fun updateFavoriteMovie(movie: Movie)
}