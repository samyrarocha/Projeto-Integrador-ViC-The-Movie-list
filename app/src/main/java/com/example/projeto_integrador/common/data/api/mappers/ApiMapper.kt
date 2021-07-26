package com.example.projeto_integrador.common.data.api.mappers


interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}