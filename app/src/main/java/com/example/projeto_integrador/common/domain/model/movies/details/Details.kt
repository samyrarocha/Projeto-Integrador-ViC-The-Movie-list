package com.example.projeto_integrador.common.domain.model.movies.details

import com.example.projeto_integrador.common.domain.model.movies.Genre
import com.example.projeto_integrador.common.domain.model.movies.details.credits.Credits

data class Details(
    val detailsOverview: String,
    val detailsReleasedYear: String,
    val detailsRuntime: String,
    val detailsGenreName: List<Genre>,
    val credits: List<Credits>
)
