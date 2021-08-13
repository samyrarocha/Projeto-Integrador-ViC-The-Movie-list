package com.example.projeto_integrador.features.moviedetails

import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.ui.UIGenre
import com.example.projeto_integrador.features.feed.data.ui.UIMovie

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movies: List<UIMovie> = emptyList(),
    val genre: List<UIGenre> = emptyList(),
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null
)
