package com.example.projeto_integrador.common.domain.model.movies

data class SearchResults(
    val searchMovieId: Long,
    val searchMovieTitle: String,
    val searchVoteAverage: Int,
    val favorite: Boolean = false,
    val searchPosterPath: String
) {
    val aproval: Int
        get() = searchVoteAverage.times(10)

}

