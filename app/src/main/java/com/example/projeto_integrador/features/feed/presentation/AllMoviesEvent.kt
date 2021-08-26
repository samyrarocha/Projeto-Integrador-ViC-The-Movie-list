package com.example.projeto_integrador.features.feed.presentation

import com.example.projeto_integrador.features.feed.data.mappers.models.UIGenre
import com.example.projeto_integrador.features.feed.data.mappers.models.UIMovie

sealed class AllMoviesEvent{
    object RequestInitialMoviesList: AllMoviesEvent()
    object RequestMoreMovies: AllMoviesEvent()
    data class UpdateGenre(val selectedGenre: UIGenre): AllMoviesEvent()
    data class QueryInput(val input: String): AllMoviesEvent()
    data class UpdateFavoriteMovie(val uiMovie: UIMovie):AllMoviesEvent()
    object GetFavoriteMovies: AllMoviesEvent()
    object PrepareForSearch : AllMoviesEvent()
}

