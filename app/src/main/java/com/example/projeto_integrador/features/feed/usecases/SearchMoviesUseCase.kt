package com.example.projeto_integrador.features.feed.usecases

import com.example.projeto_integrador.common.domain.model.movies.Search
import com.example.projeto_integrador.common.domain.model.movies.SearchResults
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.models.SearchParameters

class SearchMoviesUseCase(
    private val moviesRepository: MoviesRepository
){
    suspend operator fun invoke(
        query: String
    ): Search {

        return moviesRepository.searchMovies(1, SearchParameters(query))
    }

}