package com.example.projeto_integrador.features.feed.data.ui

class UIDetails(
    val detailsOverview: String,
    val detailsReleasedYear: String,
    val detailsRuntime: String,
    val detailsGenreName: List<UIGenre>,
    val credits: List<UICredits>
)

class UICredits(
    val creditsMovieId: Long?,
    val creditCast: List<UICast>,
    val creditCrew: List<UICrew>
)

class UICast(
    val castId: Long,
    val castCharacter: String,
    val castName: String,
    val castPosterPath: String
)

class UICrew(
    val crewId: Long,
    val crewName: String,
    val crewJob: String,
    val crewPosterPath: String
)