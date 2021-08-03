package com.example.projeto_integrador.common.data

import com.example.projeto_integrador.common.data.api.models.*
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import okhttp3.Cache

class MoviesRepositoryImpl (
    private val api: TmdbApi,
    private val cache: Cache,
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