package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.NoMoreItemsException
import com.example.projeto_integrador.domain.model.movies.Genre
import com.example.projeto_integrador.domain.repository.GenreRepository

const val ERROR_MESSAGE = "Empty genre list :("

class GenreListUseCase(
    private val genreRepository: GenreRepository
) {

    suspend operator fun invoke(): List<Genre> {
        val genreList = genreRepository.requestGenreList()
        if (genreList.isEmpty()){
            throw NoMoreItemsException(ERROR_MESSAGE)
        } else{
            return genreList
        }
    }
}