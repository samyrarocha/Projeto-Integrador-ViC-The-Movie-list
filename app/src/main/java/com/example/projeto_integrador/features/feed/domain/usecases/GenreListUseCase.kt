package com.example.projeto_integrador.features.feed.domain.usecases

import com.example.projeto_integrador.common.domain.model.movies.Genre
import com.example.projeto_integrador.common.domain.repositories.GenreRepository

class GenreListUseCase(
    private val genreRepository: GenreRepository
) {

    suspend operator fun invoke(): List<Genre> {
        return genreRepository.requestGenreList()
    }
}