package com.example.projeto_integrador.features.feed.data.ui

data class UIDetails(
    val detailsOverview: String = "",
    val detailsReleasedYear: String = "",
    val detailsRuntime: String = "",
    var detailsGenreName: List<UIGenre> = emptyList(),
    val credits: List<UICredits> = emptyList()
)

data class UICredits(
    val creditsMovieId: Long? = null,
    val creditCast: List<UICast> = emptyList(),
    val creditCrew: List<UICrew> = emptyList()
)

data class UICast(
    val castId: Long = 0,
    val castCharacter: String = "",
    val castName: String = "",
    val castPosterPath: String = ""
)

data class UICrew(
    val crewId: Long = 0,
    val crewName: String = "",
    val crewJob: String = "",
    val crewPosterPath: String = ""
)