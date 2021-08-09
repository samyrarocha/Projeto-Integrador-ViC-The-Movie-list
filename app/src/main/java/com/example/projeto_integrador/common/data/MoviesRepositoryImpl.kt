package com.example.projeto_integrador.common.data

import android.accounts.NetworkErrorException
import com.example.projeto_integrador.common.data.api.models.*
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Movie
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import io.reactivex.Flowable
import io.reactivex.rxkotlin.toFlowable
import retrofit2.HttpException

class MoviesRepositoryImpl (
    private val api: TmdbApi,
    private val apiDiscoverMapper: ApiDiscoverMapper
    ): MoviesRepository {

    var movies: List<Movie> = emptyList()

    fun getMovies(): Flowable<List<Movie>> {
        return movies.toFlowable().map { movies }
    }

    override suspend fun requestMoreMovies(pageToLoad: Int, genreFilter: String?): Discover {
        try {
            val apiDiscover = api.getDiscover(
                pageToLoad,
                genreFilter,
                ApiParameterValues.LANGUAGE_VALUE
            )

            return apiDiscoverMapper.mapToDomain(apiDiscover)

        } catch (exception: HttpException) {
            throw NetworkErrorException(exception.message ?: "Code ${exception.code()}")
        }

    }

    fun storeMovies(movies: List<Movie>) {
        this.movies = movies
    }



}