package com.example.projeto_integrador.common.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPaginatedMovies(
    @field:Json(name = "movie") val movie: List<ApiDiscover>,
    @field:Json(name = "pagination") val pagination: ApiPagination
)

@JsonClass(generateAdapter = true)
data class ApiPagination(
    @field:Json(name = "page") val currentPage: Int,
    @field:Json(name = "total_results") val totalCount: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)