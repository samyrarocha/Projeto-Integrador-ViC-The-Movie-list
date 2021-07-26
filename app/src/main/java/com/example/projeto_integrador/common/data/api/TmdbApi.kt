package com.example.projeto_integrador.common.data.api

import com.example.projeto_integrador.data.ApiConstants
import com.example.projeto_integrador.data.ApiParameters
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET(ApiConstants.DISCOVER_ENDPOINT) // 1
    suspend fun getdiscover( // 2
        @Query(ApiParameters.PAGE) pageToLoad: Int, // 3
        @Query(ApiParameters.DISCOVER_GENRE) genreFilter: Int,
    ) // 4


}