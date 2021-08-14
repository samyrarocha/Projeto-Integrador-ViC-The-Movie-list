package com.example.projeto_integrador.common.data

import android.telecom.Call
import com.example.projeto_integrador.common.data.api.models.*
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.data.api.models.mappers.ApiSearchMapper
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Movie
import com.example.projeto_integrador.common.domain.model.movies.Search
import com.example.projeto_integrador.common.domain.model.movies.details.Details
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.search.domain.model.SearchParameters

class MoviesRepositoryImpl(
    private val api: TmdbApi,
    private val apiDiscoverMapper: ApiDiscoverMapper,
    private val apiSearchMapper: ApiSearchMapper
): MoviesRepository {

    override suspend fun requestMoreMovies(pageToLoad: Int, genreFilter: String?): Discover {

            val apiDiscover = api.getDiscover(
                pageToLoad,
                genreFilter,
                ApiParameterValues.LANGUAGE_VALUE
            )

            return apiDiscoverMapper.mapToDomain(apiDiscover)
    }

    override suspend fun searchMovies(
        pageToLoad: Int,
        searchParameters: SearchParameters
    ): Search {

        val apiSearch = api.getSearch(
            pageToLoad,
            searchParameters.query,
            ""
        )

        return apiSearchMapper.mapToDomain(apiSearch)

    }

    override suspend fun getMovieDetails(movieId: Long): ApiMovieDetails {

        return api.getMovieDetails(
            movieId = movieId,
            ApiParameterValues.LANGUAGE_VALUE,
            ApiParameters.CREDITS)

    }
}