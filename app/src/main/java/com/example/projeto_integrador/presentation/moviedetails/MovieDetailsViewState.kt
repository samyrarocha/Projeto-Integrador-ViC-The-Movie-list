package com.example.projeto_integrador.presentation.moviedetails

import com.example.projeto_integrador.presentation.feed.models.Event
import com.example.projeto_integrador.presentation.models.UIMovie
import com.example.projeto_integrador.presentation.models.UIMovieDetails

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movieDetails: UIMovieDetails? = null,
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null,
    val favoriteMovie: List<UIMovie> = emptyList()
)
