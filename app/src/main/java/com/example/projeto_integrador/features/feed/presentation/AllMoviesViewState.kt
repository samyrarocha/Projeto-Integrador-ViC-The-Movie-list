package com.example.projeto_integrador.features.feed.presentation

import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.UIMovie

data class AllMoviesViewState(
    val loading: Boolean = true,
    val movies: List<UIMovie> = emptyList(),
    val noMoreMovies: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null
)
