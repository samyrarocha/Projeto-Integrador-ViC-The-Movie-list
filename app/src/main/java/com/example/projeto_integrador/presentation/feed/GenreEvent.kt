package com.example.projeto_integrador.presentation.feed

sealed class GenreEvent {
    object RequestGenreList: GenreEvent()
}