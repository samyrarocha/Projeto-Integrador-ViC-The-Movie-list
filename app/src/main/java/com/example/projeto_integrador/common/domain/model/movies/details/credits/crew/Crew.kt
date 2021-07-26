package com.example.projeto_integrador.common.domain.model.movies.details.credits.crew

import android.media.Image
import com.example.projeto_integrador.common.domain.model.movies.details.credits.CreditsGender

data class Crew(
    val crewId: Int,
    val gender: CreditsGender,
    val crewName: String,
    val job: CrewJob,
    val image: Image
)