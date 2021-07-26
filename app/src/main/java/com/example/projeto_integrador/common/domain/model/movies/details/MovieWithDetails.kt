package com.example.projeto_integrador.common.domain.model.movies.details

import com.example.projeto_integrador.common.domain.model.movies.Genre


class MovieWithDetails (
    val id: Long,
    val title: String,
    val voteAverage: Int,
    val favorite: Boolean,
    val details: Details
    )