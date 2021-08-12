package com.example.projeto_integrador.features.feed.presentation

sealed class GenreEvent {
    object RequestGenreList: GenreEvent()
}