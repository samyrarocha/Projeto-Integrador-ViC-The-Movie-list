package com.example.projeto_integrador.features.search.presentation

import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.ui.UIMovie


class SearchViewState(
    val noSearchQuery: Boolean = true,
    val searchResults: List<UIMovie> = emptyList(),
    val searchingMovies: Boolean = false,
    val failure: Event<Throwable>? = null
) {


}