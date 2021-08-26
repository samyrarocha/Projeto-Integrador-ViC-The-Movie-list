package com.example.projeto_integrador.features.feed.data.mappers.mappers

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}