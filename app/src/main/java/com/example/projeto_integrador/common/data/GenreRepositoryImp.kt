package com.example.projeto_integrador.common.data

import com.example.projeto_integrador.common.data.api.models.ApiParameterValues
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.common.data.api.models.mappers.ApiGenreMapper
import com.example.projeto_integrador.common.domain.model.movies.Genre
import com.example.projeto_integrador.common.domain.repositories.GenreRepository

class GenreRepositoryImp (
    private val api: TmdbApi,
    private val apiGenreMapper: ApiGenreMapper
        ): GenreRepository {

    override suspend fun requestGenreList(): List<Genre> {
        val apiGenre = api.getGenre(
            ApiParameterValues.LANGUAGE_VALUE
        )
        return apiGenre.genreList.map { apiGenreMapper.mapToDomain(it) }
    }
}