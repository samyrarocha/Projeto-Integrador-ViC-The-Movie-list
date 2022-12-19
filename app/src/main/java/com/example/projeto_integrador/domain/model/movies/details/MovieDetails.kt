package com.example.projeto_integrador.domain.model.movies.details

import com.example.projeto_integrador.domain.model.movies.Genre
import com.example.projeto_integrador.domain.model.movies.details.credits.Credits
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

data class MovieDetails(
    val detailsNameTitle:String?,
    val detailsVoteAverage: Float?,
    val detailsOverview: String?,
    val detailsReleasedYear: String?,
    val detailsRuntime: Int?,
    val detailsGenreName: List<Genre>,
    val detailsBackDropPath: String?,
    val credits: Credits
){
    val popularity: String?
        get() = detailsVoteAverage?.times(10)?.toInt().toString() +"%"

    val dateFormatter = LocalDate.parse(detailsReleasedYear, DateTimeFormatter.ISO_LOCAL_DATE)
    val releasedYear = dateFormatter.year.toString()

    @ExperimentalTime
    val movieDuration = detailsRuntime?.let {
        it.toDuration(DurationUnit.MINUTES)
        .toComponents { hours, minutes, seconds, nanoseconds ->  "$hours h $minutes min"}
    }
}
