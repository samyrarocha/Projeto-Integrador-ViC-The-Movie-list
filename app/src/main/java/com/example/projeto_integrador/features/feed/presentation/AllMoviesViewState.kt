package com.example.projeto_integrador.features.feed.presentation

import com.example.projeto_integrador.common.domain.model.movies.Genre
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.UIGenre
import com.example.projeto_integrador.features.feed.data.models.UIMovie

data class AllMoviesViewState(
    val loading: Boolean = true,
    val movies: List<UIMovie> = emptyList(),
    val genre: List<UIGenre> = emptyList(),
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null
)
