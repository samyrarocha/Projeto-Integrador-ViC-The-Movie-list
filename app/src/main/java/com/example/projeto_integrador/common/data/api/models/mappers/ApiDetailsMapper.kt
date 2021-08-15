package com.example.projeto_integrador.common.data.api.models.mappers

import com.example.projeto_integrador.common.data.api.models.*
import com.example.projeto_integrador.common.domain.model.movies.details.Details
import com.example.projeto_integrador.common.domain.model.movies.details.credits.Credits
import com.example.projeto_integrador.common.domain.model.movies.details.credits.cast.Cast
import com.example.projeto_integrador.common.domain.model.movies.details.credits.crew.Crew



//class ApiDetailsMapper constructor(
//    private val apiGenreMapper:
//    ): ApiMapper<ApiMovieDetails, Details> {
//
//    override fun mapToDomain(apiEntity: ApiMovieDetails): Details {
//        return Details(
//            detailsOverview = apiEntity.overview,
//            detailsGenreName = apiGenreMapper.mapToDomain(apiEntity.detailsGenreId),
//            creditCrew = apiCrewMapper.mapToDomain(apiEntity.creditCrew)
//        )
//    }
//}


