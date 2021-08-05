package com.example.projeto_integrador.features.feed.data.models.mappers

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}