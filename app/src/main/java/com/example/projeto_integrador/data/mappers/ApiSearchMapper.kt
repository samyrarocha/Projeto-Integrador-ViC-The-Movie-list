package com.example.projeto_integrador.data.mappers

import com.example.projeto_integrador.data.api.models.ApiSearch
import com.example.projeto_integrador.data.api.models.ApiSearchResults
import com.example.projeto_integrador.domain.model.movies.Search
import com.example.projeto_integrador.domain.model.movies.SearchResults

class ApiSearchMapper(
    private val apiSearchResultsMapper: ApiSearchResultsMapper
): ApiMapper<ApiSearch, Search> {

    override fun mapToDomain(apiEntity: ApiSearch): Search {
        return Search(
            searchPage = apiEntity.searchPage,
            searchResults = apiSearchResultsMapper.mapToDomain(apiEntity.searchResults),
            searchTotalPages = apiEntity.totalPages,
            searchTotalResults = apiEntity.totalResults
        )
    }
}

class ApiSearchResultsMapper:
    ApiMapper<List<ApiSearchResults>, List<SearchResults>> {
    override fun mapToDomain(apiEntity: List<ApiSearchResults>): List<SearchResults> {
        val searchResultsList: MutableList<SearchResults> = mutableListOf()
        for (item in apiEntity) {
            val searchResults = SearchResults(
                searchMovieId = item.searchMovieId,
                searchMovieTitle = item.searchMovieTitle,
                searchPosterPath = item.searchPosterPath,
                searchVoteAverage = item.searchVoteAverage
            )
            searchResultsList.add(searchResults)
        }
        return searchResultsList
    }
}