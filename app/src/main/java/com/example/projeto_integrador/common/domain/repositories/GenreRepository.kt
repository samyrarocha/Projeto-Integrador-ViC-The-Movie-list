package com.example.projeto_integrador.common.domain.repositories

import com.example.projeto_integrador.common.domain.model.movies.Genre

interface GenreRepository {

    suspend fun requestGenreList(): List<Genre>
}