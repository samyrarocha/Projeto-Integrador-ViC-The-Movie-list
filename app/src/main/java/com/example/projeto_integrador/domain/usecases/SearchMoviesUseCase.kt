package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.movies.Search
import com.example.projeto_integrador.domain.repository.MoviesRepository
import com.example.projeto_integrador.presentation.feed.models.SearchParameters

class SearchMoviesUseCase(
    private val moviesRepository: MoviesRepository
){
    suspend operator fun invoke(
        query: String
    ): Search {

        return moviesRepository.searchMovies(1, SearchParameters(query))
    }

}