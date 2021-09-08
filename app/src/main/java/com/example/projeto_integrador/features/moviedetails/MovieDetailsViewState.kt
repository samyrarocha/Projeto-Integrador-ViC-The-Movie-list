package com.example.projeto_integrador.features.moviedetails

import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.mappers.UIMovieDetails
import com.example.projeto_integrador.features.feed.data.mappers.models.UIMovie

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movieDetails: UIMovieDetails? = null,
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null,
    val favoriteMovie: List<UIMovie> = emptyList()
)
