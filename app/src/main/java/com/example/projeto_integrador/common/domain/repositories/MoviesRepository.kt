package com.example.projeto_integrador.common.domain.repositories

import android.graphics.DrawFilter
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Movie
import com.example.projeto_integrador.common.domain.model.movies.details.MovieWithDetails
import io.reactivex.Flowable

interface MoviesRepository {

    suspend fun requestMoreMovies (pageToLoad: Int, genreFilter: String?): Discover

}