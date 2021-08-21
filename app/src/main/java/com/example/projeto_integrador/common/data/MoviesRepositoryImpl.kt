package com.example.projeto_integrador.common.data

import com.example.projeto_integrador.common.data.api.models.*
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.data.api.models.mappers.ApiMovieDetailsMapper
import com.example.projeto_integrador.common.data.api.models.mappers.ApiSearchMapper
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Search
import com.example.projeto_integrador.common.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.models.SearchParameters

class MoviesRepositoryImpl(
    private val api: TmdbApi,
    private val apiDiscoverMapper: ApiDiscoverMapper,
    private val apiSearchMapper: ApiSearchMapper,
    private val apiMovieDetailsMapper: ApiMovieDetailsMapper
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

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {

        val apiMovieDetails = api.getMovieDetails(
            movieId = movieId,
            ApiParameterValues.LANGUAGE_VALUE,
            ApiParameters.CREDITS)

        return apiMovieDetailsMapper.mapToDomain(apiMovieDetails)

    }
}