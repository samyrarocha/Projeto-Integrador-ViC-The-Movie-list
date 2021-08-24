package com.example.projeto_integrador.features.feed.data.ui.mappers

import com.example.projeto_integrador.common.domain.model.movies.Movie
import com.example.projeto_integrador.features.feed.data.ui.UIMovie

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