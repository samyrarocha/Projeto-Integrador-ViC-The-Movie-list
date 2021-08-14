package com.example.projeto_integrador.common.data.api.models

import com.example.projeto_integrador.common.domain.model.movies.details.credits.Credits
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET(ApiConstants.DISCOVER_ENDPOINT) // 1
    suspend fun getDiscover( // 2
        @Query(ApiParameters.PAGE) pageToLoad: Int, // 3
        @Query(ApiParameters.DISCOVER_GENRE) genreFilter: String?,
        @Query(ApiParameters.LANGUAGE) languageValue: String
    ): ApiDiscover// 4

    @GET(ApiConstants.SEARCH_ENDPOINT) // 1
    suspend fun getSearch( // 2
        @Query(ApiParameters.PAGE) pageToLoad: Int, // 3
        @Query(ApiParameters.QUERY) searchQuery: String,
        @Query(ApiParameters.MOVIE_APPEND_TO_RESPONSE) appendToResponse: String
    ): ApiSearch// 4

    @GET(ApiConstants.MOVIES_ENDPOINT) // 1
    suspend fun getMovie( // 2
        @Path(ApiParameters.MOVIE_ID) movieId: Long,
        @Query(ApiParameters.LANGUAGE) dataLanguage: String,
        @Query(ApiParameters.MOVIE_APPEND_TO_RESPONSE) appendToResponse: String
    ): ApiMovie// 4

    @GET("movie/{movieId}?append_to_response=images,videos,reviews") // 1
    suspend fun getMovieDetails( // 2
        @Path(ApiParameters.MOVIE_ID) movieId: Long,
        @Query(ApiParameters.LANGUAGE) dataLanguage: String,
        @Query(ApiParameters.MOVIE_APPEND_TO_RESPONSE) appendToResponse: String
    ): ApiMovieDetails// 4

    @GET(ApiConstants.GENRE_ENDPOINT) // 1
    suspend fun getGenre( // 2
        @Query(ApiParameters.LANGUAGE) dataLanguage: String// 3
    ): ApiGenreList// 4

}