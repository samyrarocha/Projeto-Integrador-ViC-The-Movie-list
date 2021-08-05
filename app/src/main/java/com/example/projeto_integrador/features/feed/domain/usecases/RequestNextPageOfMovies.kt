package com.example.projeto_integrador.features.feed.domain.usecases

import com.example.projeto_integrador.common.data.MoviesRepositoryImpl
import com.example.projeto_integrador.common.domain.model.NoMoreMoviesException
import com.example.projeto_integrador.common.domain.model.movies.Discover


class RequestNextPageOfMovies (
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) {

    suspend operator fun invoke(
        pageToLoad: Int
    ): Discover {
        val discover = moviesRepositoryImpl.requestMoreMovies(pageToLoad,"")

        if(discover.movies.isEmpty()) {
            throw NoMoreMoviesException("No more movies to show")
        }

        moviesRepositoryImpl.storeMovies(discover.movies)

        return discover

    }
}