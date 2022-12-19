package com.example.projeto_integrador.data.mappers

import com.example.projeto_integrador.data.api.models.ApiDiscover
import com.example.projeto_integrador.data.api.models.ApiMovie
import com.example.projeto_integrador.domain.model.movies.Discover
import com.example.projeto_integrador.domain.model.movies.Movie

class ApiDiscoverMapper constructor(
    private val apiMovieMapper: ApiMovieMapper,
): ApiMapper<ApiDiscover, Discover> {

    override fun mapToDomain(apiEntity: ApiDiscover): Discover {
        return Discover(
            page = apiEntity.discoverPage,
            movies = apiMovieMapper.mapToDomain(apiEntity.discoverResults),
            numberOfItems = apiEntity.numberOfItems,
            totalPages = apiEntity.totalPages
        )
    }
}

class ApiMovieMapper: ApiMapper<List<ApiMovie>, List<Movie>> {

    override fun mapToDomain(apiEntity: List<ApiMovie>): List<Movie> {
        val movieResultList: MutableList<Movie> = mutableListOf()
        for (item in apiEntity) {
            val movie = Movie(
                discoverMovieId = item.discoverMovieId,
                discoverMovieTitle = item.discoverMovieTitle,
                discoverPosterPath = item.discoverPosterPath,
                discoverVoteAverage = item.discoverVoteAverage
            )
            movieResultList.add(movie)
        }
        return movieResultList
    }
}