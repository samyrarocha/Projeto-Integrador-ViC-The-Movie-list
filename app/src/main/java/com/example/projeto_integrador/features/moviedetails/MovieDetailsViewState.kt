package com.example.projeto_integrador.features.moviedetails

import com.example.projeto_integrador.common.data.api.models.ApiCredits
import com.example.projeto_integrador.common.data.api.models.ApiGenre
import com.example.projeto_integrador.common.data.api.models.ApiMovieDetails
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.ui.UICredits
import com.example.projeto_integrador.features.feed.data.ui.UIGenre
import com.example.projeto_integrador.features.feed.data.ui.UIMovieDetails

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movieDetails: UIMovieDetails? = null,
    val noMoreMovies: Boolean = false,
    val noMoreGenre: Boolean = false,
    val failure: Event<Throwable>? = null,
    val message: Int? = null
)
