package com.example.projeto_integrador.common.data.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiCredits (
    @field:Json(name = "cast") val creditCast: List<ApiCast>,
    @field:Json(name = "crew") val creditCrew: List<ApiCrew>,
)

@JsonClass(generateAdapter = true)
data class ApiCast (
    @field:Json(name = "id") val castId: Long,
    @field:Json(name = "profile_path") val castPosterPath: String?,
    @field:Json(name = "name") val castName: String,
    @field:Json(name = "character") val castCharacter: String,
)

@JsonClass(generateAdapter = true)
data class ApiCrew (
    @field:Json(name = "id") val crewId: Long,
    @field:Json(name = "profile_path") val crewPosterPath: String?,
    @field:Json(name = "name") val crewName: String,
    @field:Json(name = "job") val crewJob: String,
)