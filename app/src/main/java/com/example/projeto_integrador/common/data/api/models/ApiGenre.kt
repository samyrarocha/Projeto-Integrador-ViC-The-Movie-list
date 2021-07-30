package com.example.projeto_integrador.common.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiGenre (
    @field:Json(name = "id") val genreId: Long?,
    @field:Json(name = "name") val genreName: String?,
)