package com.example.projeto_integrador.domain.mappers

import com.example.projeto_integrador.domain.model.movies.Movie
import com.example.projeto_integrador.presentation.models.UIMovie

class UiMovieMapper: UiMapper<Movie, UIMovie> {

    override fun mapToView(input: Movie): UIMovie {
        return UIMovie(
            id = input.discoverMovieId,
            name = input.discoverMovieTitle,
            image = input.discoverPosterPath,
            popularity = input.popularity,
            favorite = input.favorite
        )
    }
}