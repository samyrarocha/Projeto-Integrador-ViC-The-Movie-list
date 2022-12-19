package com.example.projeto_integrador.domain.model.movies.details.credits.crew

data class Crew(
    val crewId: Long,
    val crewName: String,
    val crewJob: String,
    val crewPosterPath: String?
)