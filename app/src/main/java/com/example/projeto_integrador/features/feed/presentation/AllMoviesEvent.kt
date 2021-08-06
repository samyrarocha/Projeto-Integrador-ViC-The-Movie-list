package com.example.projeto_integrador.features.feed.presentation

sealed class AllMoviesEvent{
    object RequestInitialMoviesList: AllMoviesEvent()
    object RequestMoreMovies: AllMoviesEvent()
}
