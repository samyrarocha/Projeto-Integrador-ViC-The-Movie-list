package com.example.projeto_integrador.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiDiscover(
    @field:Json(name = "page") val discoverPage: Int,
    @field:Json(name = "results") val discoverResults: List<ApiMovie>,
    @field:Json(name = "total_results") val numberOfItems: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)

@JsonClass(generateAdapter = true)
data class ApiMovie(
    @field:Json(name = "poster_path") val discoverPosterPath: String,
    @field:Json(name = "id") val discoverMovieId: Long,
    @field:Json(name = "title") val discoverMovieTitle: String,
    @field:Json(name = "vote_average") val discoverVoteAverage: Float
)