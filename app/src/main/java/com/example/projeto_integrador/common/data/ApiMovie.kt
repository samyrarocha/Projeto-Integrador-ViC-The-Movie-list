package com.example.projeto_integrador.common.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMovie (
    @field:Json(name = "movie_id") val detailsMovieId: Long?,
    @field:Json(name = "genre_id") val detailsGenreId: List<Int>?,
    @field:Json(name = "poster_path") val detailsPosterPath: String?,
    @field:Json(name = "title") val detailsTitle:String?,
    @field:Json(name = "runtime") val detailsRuntime: Int?,
    @field:Json(name = "vote_average") val detailsVoteAverage: Long?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "release_date") val releaseDate: String?,
)

@JsonClass(generateAdapter = true)
data class ApiCast (
    @field:Json(name = "id") val castId: Int?,
    @field:Json(name = "name") val castName: String?,
    @field:Json(name = "profile_path") val castProfilePath: String?,
    @field:Json(name = "known_for_department") val castDepartment: String?,
)

@JsonClass(generateAdapter = true)
data class ApiGenre (
    @field:Json(name = "genre_id") val genreId: Long?,
    @field:Json(name = "genre_name") val genreName: String?,
)

@JsonClass(generateAdapter = true)
data class ApiDiscover (
    @field:Json(name = "movie_id") val discoverMovieId: Long?,
    @field:Json(name = "poster_path") val discoverPosterPath: String?,
    @field:Json(name = "title") val discoverTitle: String?,
    @field:Json(name = "vote_average") val discoverVoteAverage: Long?,
)

@JsonClass(generateAdapter = true)
data class ApiSearch (
    @field:Json(name = "movie_id") val searchMovieId: Long?,
    @field:Json(name = "poster_path") val searchPosterPath: String?,
    @field:Json(name = "title") val searchTitle: String?,
    @field:Json(name = "vote_average") val serachVoteAverage: Long?,
)