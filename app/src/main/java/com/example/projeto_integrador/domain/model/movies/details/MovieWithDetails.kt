package com.example.projeto_integrador.domain.model.movies.details


class MovieWithDetails (
    val discoverMovieId: Long,
    val discoverMovieTitle: String,
    val discoverVoteAverage: Int,
    val favorite: Boolean,
    val discoverPosterPath: String,
    val movieDetails: MovieDetails
) {
    val aproval: Int
        get() = discoverVoteAverage.times(10)

}