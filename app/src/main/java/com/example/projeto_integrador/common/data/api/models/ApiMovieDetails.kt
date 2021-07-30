package com.example.projeto_integrador.common.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMovieDetails (
    @field:Json(name = "id") val detailsMovieId: Long?,
    @field:Json(name = "genres") val detailsGenreId: List<ApiGenre>?,
    @field:Json(name = "poster_path") val detailsPosterPath: String?,
    @field:Json(name = "title") val detailsTitle:String?,
    @field:Json(name = "runtime") val detailsRuntime: Int?,
    @field:Json(name = "vote_average") val detailsVoteAverage: Long?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "release_date") val releaseDate: String?,
)


