package com.example.projeto_integrador.common.data

import com.example.projeto_integrador.common.data.api.models.ApiParameterValues
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(
    private val api: TmdbApi,
    private val apiDiscoverMapper: ApiDiscoverMapper
): MoviesRepository {

    override suspend fun requestMoreMovies(pageToLoad: Int, genreFilter: String?): Discover {

            val apiDiscover = api.getDiscover(
                pageToLoad,
                genreFilter,
                ApiParameterValues.LANGUAGE_VALUE
            )

            return apiDiscoverMapper.mapToDomain(apiDiscover)
    }

}