package com.example.projeto_integrador.features.feed.data.mappers.mappers

import com.example.projeto_integrador.common.domain.model.movies.Genre
import com.example.projeto_integrador.features.feed.data.mappers.models.UIGenre

class UiGenreMapper: UiMapper<Genre, UIGenre> {

    override fun mapToView(input: Genre): UIGenre {
        return UIGenre(
            id = input.genreId,
            name = input.genreName
        )
    }
}