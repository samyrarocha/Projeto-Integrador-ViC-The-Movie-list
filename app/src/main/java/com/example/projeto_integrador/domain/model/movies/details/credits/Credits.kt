package com.example.projeto_integrador.domain.model.movies.details.credits

import com.example.projeto_integrador.domain.model.movies.details.credits.cast.Cast
import com.example.projeto_integrador.domain.model.movies.details.credits.crew.Crew

data class Credits(
    val creditCast: List<Cast>,
    val creditCrew: List<Crew>
)