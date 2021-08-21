package com.example.projeto_integrador.features.feed.presentation

import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.ui.UIGenre
import com.example.projeto_integrador.features.feed.data.ui.UIMovie

data class AllMoviesViewState(
    val loading: Boolean = true,
    val movies: List<UIMovie> = emptyList(),
    val genre: List<UIGenre> = emptyList(),
    val searchResults: List<UIMovie> = emptyList(),
    val noSearchQuery: Boolean = true,
    val searchingMovies: Boolean = false,
    val noRemoteResults: Boolean = false,
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null
) {
    fun updateToNoSearchQuery(): AllMoviesViewState {
        return copy(
            noSearchQuery = true,
            searchResults = emptyList(),
            noRemoteResults = false
        )
    }

    fun updateToSearching(): AllMoviesViewState {
        return copy(
            noSearchQuery = false,
            searchingMovies = false,
            noRemoteResults = false
        )
    }

    fun updateToSearchingMovies(): AllMoviesViewState {
        return copy(
            searchingMovies = true,
            searchResults = emptyList()
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

    fun updateToNoResultsAvailable(): AllMoviesViewState {
        return copy(searchingMovies = false, noRemoteResults = true)
    }

    fun updateToHasFailure(throwable: Throwable): AllMoviesViewState {
        return copy(failure = Event(throwable))
    }
}