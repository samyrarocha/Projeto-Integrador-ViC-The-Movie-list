package com.example.projeto_integrador.domain.repository

import com.example.projeto_integrador.domain.model.movies.Genre

interface GenreRepository {

    suspend fun requestGenreList(): List<Genre>
}