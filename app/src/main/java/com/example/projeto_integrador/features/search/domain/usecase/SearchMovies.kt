package com.example.projeto_integrador.features.search.domain.usecase

import com.example.projeto_integrador.common.domain.model.NoMoreMoviesException
import com.example.projeto_integrador.common.domain.model.movies.Search
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.search.domain.model.SearchParameters

class SearchMovies(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        searchParameters: SearchParameters
    ) {
        val searchResults = moviesRepository.searchMovies(pageToLoad, searchParameters)

        if (searchResults.searchResults.isEmpty()) {
            throw NoMoreMoviesException("Esse filme não existe")
        }
    }
}