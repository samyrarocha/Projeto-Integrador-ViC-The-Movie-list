package com.example.projeto_integrador.common.domain.model.movies.details.credits.cast

import android.media.Image
import com.example.projeto_integrador.common.domain.model.movies.details.credits.CreditsGender

data class Cast(
    val castId: Int,
    val gender: CreditsGender,
    val castName: String,
    val image: Image
)