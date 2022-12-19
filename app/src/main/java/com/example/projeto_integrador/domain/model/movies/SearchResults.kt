package com.example.projeto_integrador.domain.model.movies

data class SearchResults(
    val searchMovieId: Long,
    val searchMovieTitle: String,
    val searchVoteAverage: Float,
    val favorite: Boolean = false,
    val searchPosterPath: String
) {
    val popularity: Float
        get() = searchVoteAverage.times(10)

}

