package com.example.projeto_integrador.common.domain.model.movies.details.credits

import com.example.projeto_integrador.common.domain.model.movies.details.credits.cast.Cast
import com.example.projeto_integrador.common.domain.model.movies.details.credits.crew.Crew

data class Credits(
    val movieId: Int,
    val cast: List<Cast>,
    val crew: List<Crew>,
)