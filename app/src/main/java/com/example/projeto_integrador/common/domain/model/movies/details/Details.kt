package com.example.projeto_integrador.common.domain.model.movies.details

import com.example.projeto_integrador.common.domain.model.movies.Genre
import com.example.projeto_integrador.common.domain.model.movies.details.credits.Credits

data class Details(
    val overview: String,
    val releasedYear: String,
    val runtime: String,
    val genreName: List<Genre>,
    val credits: Credits
    //val certification: Certification
)
