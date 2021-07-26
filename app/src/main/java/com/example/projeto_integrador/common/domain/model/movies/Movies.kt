package com.example.projeto_integrador.common.domain.model.movies

import android.media.Image

data class Movies(
    val id: Long,
    val title: String,
    val voteAverage: Int,
    val favorite: Boolean,
    val poster: Image
) {
    val aproval: Int
        get() = voteAverage.times(10)

}