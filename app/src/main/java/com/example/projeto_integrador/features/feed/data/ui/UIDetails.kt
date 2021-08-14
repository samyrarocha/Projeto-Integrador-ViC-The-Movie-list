package com.example.projeto_integrador.features.feed.data.ui

data class UIDetails(
    val detailsOverview: String,
    val detailsReleasedYear: String,
    val detailsRuntime: String,
    val detailsGenreName: List<UIGenre>,
    val credits: List<UICredits>
)

data class UICredits(
    val creditsMovieId: Long?,
    val creditCast: List<UICast>,
    val creditCrew: List<UICrew>
)

data class UICast(
    val castId: Long,
    val castCharacter: String,
    val castName: String,
    val castPosterPath: String
)

data class UICrew(
    val crewId: Long,
    val crewName: String,
    val crewJob: String,
    val crewPosterPath: String
)