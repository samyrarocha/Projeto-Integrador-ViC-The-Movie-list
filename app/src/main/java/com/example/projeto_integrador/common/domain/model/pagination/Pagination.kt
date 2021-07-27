package com.example.projeto_integrador.common.domain.model.pagination

class Pagination(
    val currentPage: Int,
    val totalPages: Int
) {

    companion object {
        //For the case when I store the current page locally, but haven't yet requested a new page
        //from the remote source.
        const val UNKNOWN_TOTAL = -1
        const val DEFAULT_PAGE_SIZE = 1
    }

    val canLoadMore: Boolean
    get() = totalPages == UNKNOWN_TOTAL || currentPage < totalPages
}