package com.example.projeto_integrador.common.domain.model.movies

data class Search (
    val searchPage: Int,
    val searchResults: List<SearchResults>,
    val searchTotalPages: Int,
    val searchTotalResults: Int,
)
