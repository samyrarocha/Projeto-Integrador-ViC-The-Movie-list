package com.example.projeto_integrador.data.mappers


interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}