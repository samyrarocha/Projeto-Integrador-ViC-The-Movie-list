package com.example.projeto_integrador.data.repository

import com.example.projeto_integrador.data.api.models.ApiParameterValues
import com.example.projeto_integrador.data.api.models.ApiParameters
import com.example.projeto_integrador.data.api.models.TmdbApi
import com.example.projeto_integrador.data.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.data.mappers.ApiMovieDetailsMapper
import com.example.projeto_integrador.data.mappers.ApiSearchMapper
import com.example.projeto_integrador.domain.model.movies.Discover
import com.example.projeto_integrador.domain.model.movies.Movie
import com.example.projeto_integrador.domain.model.movies.Search
import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.domain.repository.MoviesRepository
import com.example.projeto_integrador.data.cache.Cache
import com.example.projeto_integrador.data.cache.model.CachedMovie
import com.example.projeto_integrador.presentation.feed.models.SearchParameters

class MoviesRepositoryImp(
    private val api: TmdbApi,
    private val apiDiscoverMapper: ApiDiscoverMapper,
    private val apiSearchMapper: ApiSearchMapper,
    private val apiMovieDetailsMapper: ApiMovieDetailsMapper,
    private val cache: Cache
): MoviesRepository {

    override suspend fun requestMovies(pageToLoad: Int, genreFilter: String?): Discover {

        val apiDiscover = api.getDiscover(
            pageToLoad,
            genreFilter,
            ApiParameterValues.LANGUAGE_VALUE
        )

        return apiDiscoverMapper.mapToDomain(apiDiscover)
    }

    override suspend fun searchMovies(
        pageToLoad: Int,
        searchParameters: SearchParameters
    ): Search {

        val apiSearch = api.getSearch(
            pageToLoad,
            searchParameters.query,
            ""
        )

        return apiSearchMapper.mapToDomain(apiSearch)

    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {

        val apiMovieDetails = api.getMovieDetails(
            movieId = movieId,
            ApiParameterValues.LANGUAGE_VALUE,
            ApiParameters.CREDITS)

        return apiMovieDetailsMapper.mapToDomain(apiMovieDetails)

    }

    override suspend fun getMovies(): List<Movie> {
        return cache.getMovies()
            .map { cachedMovie ->
                Movie(
                    cachedMovie.movieId,
                    cachedMovie.movieTitle,
                    cachedMovie.popularity,
                    cachedMovie.favorite,
                    cachedMovie.posterPath
                )
            }
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return cache.getFavoriteMovies()
            .map { cachedMovie ->
                Movie(
                    cachedMovie.movieId,
                    cachedMovie.movieTitle,
                    cachedMovie.popularity,
                    cachedMovie.favorite,
                    cachedMovie.posterPath
                )
            }
    }

    override suspend fun storeMovieList(movie: List<Movie>) {
        cache.storeNewCachedData(
            movie.map { CachedMovie.fromDomain(it) }
        )
    }

    override suspend fun updateFavoriteMovie(movie: Movie) {
        cache.updateFavoriteMovie(
            CachedMovie(
                movie.discoverMovieId,
                movie.discoverMovieTitle,
                movie.popularity,
                movie.discoverPosterPath,
                movie.favorite
            )
        )
    }
}