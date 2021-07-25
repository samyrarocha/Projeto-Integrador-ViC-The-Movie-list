package com.example.projeto_integrador.common.data.mappers


interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}