package com.example.projeto_integrador.domain.model.movies

data class Discover(
    val page: Int,
    val movies: List<Movie>,
    val numberOfItems: Int,
    val totalPages: Int
)
