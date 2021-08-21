package com.example.projeto_integrador.common.domain.repositories

import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Search
import com.example.projeto_integrador.common.domain.model.movies.SearchResults
import com.example.projeto_integrador.common.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.features.feed.data.models.SearchParameters
import io.reactivex.Flowable


interface MoviesRepository {

    suspend fun requestMoreMovies (pageToLoad: Int, genreFilter: String?): Discover

    suspend fun searchMovies (pageToLoad: Int, searchParameters: SearchParameters): Search

    suspend fun getMovieDetails(movieId: Long): MovieDetails

}