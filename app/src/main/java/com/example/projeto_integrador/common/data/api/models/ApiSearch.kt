package com.example.projeto_integrador.common.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSearch(
    @field:Json(name = "page") val searchPage: Int,
    @field:Json(name = "results") val searchResults: List<ApiSearchResults>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int,

)

@JsonClass(generateAdapter = true)
data class ApiSearchResults(
    @field:Json(name = "poster_path") val searchPosterPath: String,
    @field:Json(name = "id") val searchMovieId: Long,
    @field:Json(name = "title") val searchMovieTitle: String,
    @field:Json(name = "vote_average") val searchVoteAverage: Float,
    @field:Json(name = "genre_ids") val searchGenresId: List<Long>
)