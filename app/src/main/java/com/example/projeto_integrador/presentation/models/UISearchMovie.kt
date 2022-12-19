package com.example.projeto_integrador.presentation.models


data class UISearchMovie(
    val searchPage: Int = 0,
    val searchResults: List<UISearchResults>,
    val searchTotalPages: Int = 0,
    val searchTotalResults: Int = 0
)

data class UISearchResults(
    val searchPosterPath: String = "",
    val searchMovieId: Long = 0,
    val searchMovieTitle: String = "",
    val searchVoteAverage: String = "",
    val favorite: Boolean = false
)

