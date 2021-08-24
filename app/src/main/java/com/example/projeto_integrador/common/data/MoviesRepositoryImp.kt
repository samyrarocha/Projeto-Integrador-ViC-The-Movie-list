package com.example.projeto_integrador.common.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.projeto_integrador.common.data.api.models.ApiParameterValues
import com.example.projeto_integrador.common.data.api.models.ApiParameters
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.data.api.models.mappers.ApiMovieDetailsMapper
import com.example.projeto_integrador.common.data.api.models.mappers.ApiSearchMapper
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Movie
import com.example.projeto_integrador.common.domain.model.movies.Search
import com.example.projeto_integrador.common.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.cache.Cache
import com.example.projeto_integrador.features.feed.data.cache.model.CachedMovie
import com.example.projeto_integrador.features.feed.data.models.SearchParameters
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toObservable

class MoviesRepositoryImp(
    private val api: TmdbApi,
    private val apiDiscoverMapper: ApiDiscoverMapper,
    private val apiSearchMapper: ApiSearchMapper,
    private val apiMovieDetailsMapper: ApiMovieDetailsMapper,
    private val cache: Cache
): MoviesRepository {

    override suspend fun requestMoreMovies(pageToLoad: Int, genreFilter: String?): Discover {

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

    override suspend fun storeFavoriteMovie(movie: Movie) {
        cache.storeFavoriteMovie(
            CachedMovie(
                movie.discoverMovieId,
                movie.discoverMovieTitle,
                movie.popularity,
                movie.discoverPosterPath,
                movie.favorite
            )
        )
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        cache.deleteFavorite(
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