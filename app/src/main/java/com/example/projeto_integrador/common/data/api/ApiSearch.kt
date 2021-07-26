package com.example.projeto_integrador.common.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSearch(
    @field:Json(name = "page") val searchPage: Int,
    @field:Json(name = "results") val searchResults: List<ApiSearchResults>?
)

@JsonClass(generateAdapter = true)
data class ApiSearchResults(
    @field:Json(name = "poster_path") val searchPosterPath: String,
    @field:Json(name = "id") val searchMovieId: Long?,
    @field:Json(name = "title") val searchMovieTitle: String?,
    @field:Json(name = "vote_average") val searchVoteAverage: Long?,
    @field:Json(name = "genre_ids") val searchGenresId: List<Long>?
)