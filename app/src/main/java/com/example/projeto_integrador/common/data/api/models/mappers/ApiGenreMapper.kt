package com.example.projeto_integrador.common.data.api.models.mappers

import com.example.projeto_integrador.common.data.api.models.ApiGenre
import com.example.projeto_integrador.common.domain.model.movies.Genre


class ApiGenrerMapper: ApiMapper<ApiGenre, Genre> {

    override fun mapToDomain(apiEntity: ApiGenre): Genre {
        return Genre(
            genreId = apiEntity.genreId,
            genreName = apiEntity.genreName
        )
    }
}