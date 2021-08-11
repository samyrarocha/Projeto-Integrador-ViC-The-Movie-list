package com.example.projeto_integrador.common.domain.model.movies

import android.media.Image

data class Movie(
    val discoverMovieId: Long,
    val discoverMovieTitle: String,
    val discoverVoteAverage: Float,
    val favorite: Boolean = false,
    val discoverPosterPath: String
) {
    val popularity: Float
        get() = discoverVoteAverage.times(10)

}