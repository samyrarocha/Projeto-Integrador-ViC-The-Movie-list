package com.example.projeto_integrador.common.data.api.models.mappers

import com.example.projeto_integrador.common.data.api.models.ApiGenre
import com.example.projeto_integrador.common.domain.model.movies.Genre
import javax.inject.Inject


class ApiGenrerMapper @Inject constructor(): ApiMapper<ApiGenre, Genre> {

    override fun mapToDomain(apiEntity: ApiGenre): Genre {
        return Genre(
            genreId = apiEntity.genreId,
            genreName = apiEntity.genreName
        )
    }
}