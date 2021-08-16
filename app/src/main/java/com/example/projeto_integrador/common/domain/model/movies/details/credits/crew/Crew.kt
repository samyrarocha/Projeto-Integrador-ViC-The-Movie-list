package com.example.projeto_integrador.common.domain.model.movies.details.credits.crew

import android.media.Image
import com.example.projeto_integrador.common.domain.model.movies.details.credits.CreditsGender

data class Crew(
    val crewId: Long,
    val crewName: String,
    val crewJob: String,
    val crewPosterPath: String?
)