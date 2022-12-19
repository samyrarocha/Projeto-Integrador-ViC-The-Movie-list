package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.movies.Genre
import com.example.projeto_integrador.domain.repository.GenreRepository

class GenreListUseCase(
    private val genreRepository: GenreRepository
) {

    suspend operator fun invoke(): List<Genre> {
        return genreRepository.requestGenreList()
    }
}