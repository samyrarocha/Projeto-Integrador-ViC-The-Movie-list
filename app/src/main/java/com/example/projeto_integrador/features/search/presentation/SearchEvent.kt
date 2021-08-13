package com.example.projeto_integrador.features.search.presentation

sealed class SearchEvent {
    object PrepareForSearch : SearchEvent()
    data class QueryInput(val input: String): SearchEvent()
}