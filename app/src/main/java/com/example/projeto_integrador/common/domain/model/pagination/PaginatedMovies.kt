package com.example.projeto_integrador.common.domain.model.pagination

import com.example.projeto_integrador.common.domain.model.movies.details.MovieWithDetails

class PaginatedMovies(
    val movies: List<MovieWithDetails>,
    val pagination: Pagination
)
