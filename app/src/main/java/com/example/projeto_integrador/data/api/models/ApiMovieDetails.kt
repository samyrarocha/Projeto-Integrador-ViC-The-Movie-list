package com.example.projeto_integrador.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMovieDetails (
    @field:Json(name = "id") val detailsMovieId: Long?,
    @field:Json(name = "genres") val detailsGenres: List<ApiGenre>,
    @field:Json(name = "backdrop_path") val detailsPosterPath: String?,
    @field:Json(name = "title") val detailsTitle:String?,
    @field:Json(name = "runtime") val detailsRuntime: Int?,
    @field:Json(name = "vote_average") val detailsVoteAverage: Float?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "release_date") val releaseDate: String?,
    @field:Json(name = "credits") val credits: ApiCredits
)


