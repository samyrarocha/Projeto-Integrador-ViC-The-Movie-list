package com.example.projeto_integrador.common.data.api.models.mappers

import com.example.projeto_integrador.common.data.api.models.ApiPagination
import com.example.projeto_integrador.common.domain.model.pagination.Pagination
import javax.inject.Inject

class ApiPaginationMapper @Inject constructor(
): ApiMapper<ApiPagination, Pagination> {

    override fun mapToDomain(apiEntity: ApiPagination): Pagination {
        return Pagination(
            currentPage = apiEntity.currentPage ?: 0,
            totalPages = apiEntity.totalPages ?: 0
        )
    }

}