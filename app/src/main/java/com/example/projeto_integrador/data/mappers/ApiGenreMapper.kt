package com.example.projeto_integrador.data.mappers

import com.example.projeto_integrador.data.api.models.ApiGenre
import com.example.projeto_integrador.domain.model.movies.Genre






class ApiGenreMapper: ApiMapper<ApiGenre, Genre> {

    override fun mapToDomain(apiEntity: ApiGenre): Genre {
        return Genre(
            genreId = apiEntity.genreId,
            genreName = apiEntity.genreName
        )
    }
}