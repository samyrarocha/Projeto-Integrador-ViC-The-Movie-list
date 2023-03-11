package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.domain.model.NoSearchResultsException
import com.example.projeto_integrador.domain.model.movies.Search
import com.example.projeto_integrador.domain.repository.MoviesRepository
import com.example.projeto_integrador.presentation.feed.models.SearchParameters

class SearchMoviesUseCase(private val moviesRepository: MoviesRepository){
    suspend operator fun invoke(query: String): Search {
        val localMovieResults = moviesRepository.searchMoviesLocally("$query%")

        if (localMovieResults.isNotEmpty()) {
            return Search(
                searchTotalResults = localMovieResults.size,
                searchResults = localMovieResults,
                searchPage = 1,
                searchTotalPages = 1
            )
        }

        val remoteMovieResults = moviesRepository.searchMoviesRemote(
            1,
            SearchParameters(query)
        )
        if (remoteMovieResults.searchResults.isEmpty()){
            throw NoSearchResultsException()
        } else {
            return remoteMovieResults
        }
    }
}