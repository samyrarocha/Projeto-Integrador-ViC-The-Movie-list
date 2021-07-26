package com.example.projeto_integrador.common.domain.model.movies

data class Search (
    val searchMovieId: Long,
    val searchMovieTitle: String,
    val searchVoteAverage: Int,
    val favorite: Boolean,
    val searchPosterPath: String
) {
    val aproval: Int
        get() = searchVoteAverage.times(10)

}
