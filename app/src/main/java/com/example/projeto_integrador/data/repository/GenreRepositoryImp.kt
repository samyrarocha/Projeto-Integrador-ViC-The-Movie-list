package com.example.projeto_integrador.data.repository

import com.example.projeto_integrador.data.api.models.ApiParameterValues
import com.example.projeto_integrador.data.api.models.TmdbApi
import com.example.projeto_integrador.data.mappers.ApiGenreMapper
import com.example.projeto_integrador.domain.model.movies.Genre
import com.example.projeto_integrador.domain.repository.GenreRepository

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