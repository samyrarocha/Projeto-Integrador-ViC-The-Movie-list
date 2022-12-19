package com.example.projeto_integrador.data.mappers

import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.data.api.models.ApiMovieDetails


class ApiMovieDetailsMapper constructor(
    private val apiGenreMapper: ApiGenreMapper,
    private val apiCreditsMapper: ApiCreditsMapper
    ): ApiMapper<ApiMovieDetails, MovieDetails> {

    override fun mapToDomain(apiEntity: ApiMovieDetails): MovieDetails {
        return MovieDetails(
            detailsOverview = apiEntity.overview,
            detailsGenreName = apiEntity.detailsGenres.map { apiGenreMapper.mapToDomain(it) },
            detailsReleasedYear = apiEntity.releaseDate,
            detailsRuntime = apiEntity.detailsRuntime,
            detailsNameTitle = apiEntity.detailsTitle,
            detailsVoteAverage = apiEntity.detailsVoteAverage,
            credits = apiCreditsMapper.mapToDomain(apiEntity.credits),
            detailsBackDropPath = apiEntity.detailsPosterPath
        )
    }
}



