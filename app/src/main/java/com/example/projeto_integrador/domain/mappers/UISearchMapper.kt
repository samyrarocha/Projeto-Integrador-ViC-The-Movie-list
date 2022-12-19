package com.example.projeto_integrador.domain.mappers

import com.example.projeto_integrador.domain.model.movies.SearchResults
import com.example.projeto_integrador.presentation.models.UIMovie

class UiSearchResultsMapper: UiMapper<SearchResults, UIMovie> {

        override fun mapToView(input: SearchResults): UIMovie {
                return UIMovie(
                        id = input.searchMovieId,
                        name = input.searchMovieTitle,
                        image = input.searchPosterPath,
                        popularity = input.popularity,
                        favorite = input.favorite
                )
        }
}