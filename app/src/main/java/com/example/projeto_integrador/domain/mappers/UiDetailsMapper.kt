package com.example.projeto_integrador.domain.mappers

import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.domain.model.movies.details.credits.cast.Cast
import com.example.projeto_integrador.domain.model.movies.details.credits.crew.Crew
import com.example.projeto_integrador.presentation.models.UICredits
import com.example.projeto_integrador.presentation.models.UIMovieDetails
import kotlin.time.ExperimentalTime

class UiMovieDetailsMapper(
    private val uiGenreMapper: UiGenreMapper,
    private val uiCrewMapper: UiCrewMapper,
    private val uiCastMapper: UiCastMapper
    ): UiMapper<MovieDetails, UIMovieDetails> {

    @ExperimentalTime
    override fun mapToView(input: MovieDetails): UIMovieDetails {
        val director = input.credits.creditCrew.filter { it.crewJob == "Director" }
        var credits = director.map { uiCrewMapper.mapToView(it) }

        credits += input.credits.creditCast.map { uiCastMapper.mapToView(it) }

        return UIMovieDetails(
            detailsRuntime = input.movieDuration,
            detailsReleasedYear = input.releasedYear,
            detailsGenreName = input.detailsGenreName.map { uiGenreMapper.mapToView(it) },
            detailsOverview = input.detailsOverview,
            credits = credits,
            detailsVoteAverage = input.popularity,
            detailsMovieTitle = input.detailsNameTitle,
            detailsPosterPath = input.detailsBackDropPath
        )
    }
}

class UiCrewMapper: UiMapper<Crew, UICredits> {

    override fun mapToView(input: Crew): UICredits {
        return UICredits(
            creditsMovieId = input.crewId,
            creditsJob = input.crewJob,
            creditsName = input.crewName,
            creditsPosterPath = input.crewPosterPath
        )
    }
}

class UiCastMapper: UiMapper<Cast, UICredits> {

    override fun mapToView(input: Cast): UICredits {
        return UICredits(
            creditsMovieId = input.castId,
            creditsPosterPath = input.castPosterPath,
            creditsName = input.castName,
            creditsJob = input.castCharacter
        )
    }
}