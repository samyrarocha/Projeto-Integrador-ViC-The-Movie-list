package com.example.projeto_integrador.features.search.data

import com.example.projeto_integrador.common.domain.model.movies.SearchResults
import com.example.projeto_integrador.features.feed.data.ui.UIMovie
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiMapper

class UiSearchResultsMapper: UiMapper<SearchResults, UIMovie> {

        override fun mapToView(input: SearchResults): UIMovie {
                return UIMovie(
                        id = input.searchMovieId,
                        name = input.searchMovieTitle,
                        image = input.searchPosterPath,
                        popularity = input.popularity
                )
        }
}