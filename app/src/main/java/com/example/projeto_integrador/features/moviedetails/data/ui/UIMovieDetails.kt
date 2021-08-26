package com.example.projeto_integrador.features.feed.data.mappers

import com.example.projeto_integrador.features.feed.data.mappers.models.UIGenre

data class UIMovieDetails(
    val detailsMovieTitle: String? = "",
    val detailsOverview: String? = "",
    val detailsReleasedYear: String? = "",
    val detailsRuntime: String? = "",
    val detailsVoteAverage: String? = "",
    var detailsGenreName: List<UIGenre> = emptyList(),
    val credits: List<UICredits>,
    val detailsPosterPath: String? = ""
)

data class UICredits(
    val creditsMovieId: Long? = null,
    val creditsName: String? = "",
    val creditsJob: String = "",
    val creditsPosterPath: String? = ""
)
