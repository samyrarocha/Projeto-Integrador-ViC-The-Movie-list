package com.example.projeto_integrador.presentation.models

data class UIMovie(
    val id: Long,
    val name: String,
    val image: String,
    val popularity: Float,
    val favorite: Boolean
)