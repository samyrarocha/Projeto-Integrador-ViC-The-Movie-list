package com.example.projeto_integrador.domain.mappers

import com.example.projeto_integrador.domain.model.movies.Genre
import com.example.projeto_integrador.presentation.models.UIGenre

class UiGenreMapper: UiMapper<Genre, UIGenre> {

    override fun mapToView(input: Genre): UIGenre {
        return UIGenre(
            id = input.genreId,
            name = input.genreName
        )
    }
}