package com.example.projeto_integrador.features.feed.presentation

import com.example.projeto_integrador.features.feed.data.ui.UIGenre

sealed class AllMoviesEvent{
    object RequestInitialMoviesList: AllMoviesEvent()
    object RequestMoreMovies: AllMoviesEvent()
    data class UpdateGenre(val selectedGenre: UIGenre): AllMoviesEvent()
}

