package com.example.projeto_integrador.data.api.models

object ApiConstants {

    const val BASE_ENDPOINT = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/"

    const val MOVIES_ENDPOINT = "movie"
    const val GENRE_ENDPOINT = "genre/movie/list"
    const val SEARCH_ENDPOINT = "search/movie"
    const val DISCOVER_ENDPOINT = "discover/movie"
    const val MOVIE_DETAILS = "movie/{movieId}"
}

object ApiParameters {

    const val PAGE = "page"
    const val MOVIE_ID = "movieId"

    const val CREDITS = "credits"
    const val DISCOVER_GENRE = "with_genres"
    const val LANGUAGE = "language"
    const val QUERY = "query"
    const val MOVIE_APPEND_TO_RESPONSE = "append_to_response"

}

object ApiParameterValues {
    const val LANGUAGE_VALUE = "en-US"
}
