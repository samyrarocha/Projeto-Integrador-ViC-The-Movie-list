package com.example.projeto_integrador.common.domain.model.movies

import android.media.Image

data class Movie(
    val discoverMovieId: Long,
    val discoverMovieTitle: String,
    val discoverVoteAverage: Int,
    val favorite: Boolean = false,
    val discoverPosterPath: String
) {
    val popularity: Int
        get() = discoverVoteAverage.times(10)

}