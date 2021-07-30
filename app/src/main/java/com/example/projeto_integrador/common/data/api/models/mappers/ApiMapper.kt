package com.example.projeto_integrador.common.data.api.models.mappers


interface ApiMapper<E, D> {

    fun mapToDomain(apiEntity: E): D
}