package com.example.projeto_integrador.features.feed.presentation

import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.mappers.models.UIGenre
import com.example.projeto_integrador.features.feed.data.mappers.models.UIMovie

data class AllMoviesViewState(
    val loading: Boolean = true,
    val movies: List<UIMovie> = emptyList(),
    val genre: List<UIGenre> = emptyList(),
    val favoriteMovie: List<UIMovie> = emptyList(),
    val searchResults: List<UIMovie> = emptyList(),
    val noSearchQuery: Boolean = true,
    val searchingMovies: Boolean = false,
    val noRemoteResults: Boolean = false,
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null
) {

    fun updateMovies(movies: List<UIMovie>): AllMoviesViewState {
        var moviesCopy = mutableListOf<UIMovie>()
        moviesCopy.addAll(movies)

        for (movie in movies) {
            if (favoriteMovie.contains(movie)) {
                moviesCopy.add(UIMovie(
                    movie.id,
                    movie.name,
                    movie.image,
                    movie.popularity,
                    true))
            } else {
                moviesCopy.add(UIMovie(
                    movie.id,
                    movie.name,
                    movie.image,
                    movie.popularity,
                    true))
            }
        }

        return copy(
            loading = false,
            movies = moviesCopy
        )
    }

    fun updateFavoriteMovies(favoriteMovies: List<UIMovie>): AllMoviesViewState {
        var moviesCopy = mutableListOf<UIMovie>()
        moviesCopy.addAll(movies)

        for (movie in movies) {
            if (favoriteMovies.contains(movie)) {
                moviesCopy.add(UIMovie(
                    movie.id,
                    movie.name,
                    movie.image,
                    movie.popularity,
                    true))
            } else {
                moviesCopy.add(UIMovie(
                    movie.id,
                    movie.name,
                    movie.image,
                    movie.popularity,
                    true))
            }
        }

        return copy(
            loading = false,
            movies = moviesCopy,
            favoriteMovie = favoriteMovies
        )
    }

    fun updateToHasSearchResults(searchResults: List<UIMovie>): AllMoviesViewState {
        return copy(
            noSearchQuery = false,
            movies = searchResults,
            searchingMovies = false,
            noRemoteResults = false
        )
    }

}