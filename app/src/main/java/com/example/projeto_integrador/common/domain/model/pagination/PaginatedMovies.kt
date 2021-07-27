package com.example.projeto_integrador.common.domain.model.pagination

import com.example.projeto_integrador.common.domain.model.movies.details.MovieWithDetails

data class PaginatedAnimals(
    val animals: List<MovieWithDetails>,
    val pagination: Pagination
    )
