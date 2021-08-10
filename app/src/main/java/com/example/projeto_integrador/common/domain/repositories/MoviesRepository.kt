package com.example.projeto_integrador.common.domain.repositories

import com.example.projeto_integrador.common.domain.model.movies.Discover


interface MoviesRepository {

    suspend fun requestMoreMovies (pageToLoad: Int, genreFilter: String?): Discover


}