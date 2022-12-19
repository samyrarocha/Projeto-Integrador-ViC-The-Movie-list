package com.example.projeto_integrador.domain.mappers

interface UiMapper<E, V> {

    fun mapToView(input: E): V
}