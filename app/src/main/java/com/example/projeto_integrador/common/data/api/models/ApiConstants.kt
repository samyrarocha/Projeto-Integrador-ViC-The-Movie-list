package com.example.projeto_integrador.common.data.api.models

object ApiConstants {

    const val BASE_ENDPOINT = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/"
    const val AUTH_ENDPOINT = "https://api.themoviedb.org/3/movie/76341"
    const val CONTENT_TYPE_ENDPOINT = "application/json;charset=utf-8"

    const val MOVIES_ENDPOINT = "movies"
    const val GENRE_ENDPOINT = "genre/movie/list"
    const val SEARCH_ENDPOINT = "search/company"
    const val DISCOVER_ENDPOINT = "discover/movie"

}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer"
    const val AUTH_HEADER = "Authorization"
    const val CONTENT_TYPE_HEADER = "Content-Type"

    const val PAGE = "page"
    const val MOVIE_ID = "movie_id"
    const val IMAGES = "images"
    const val CREDITS = "credits"
    const val RELEASE_DATES = "release_dates"
    const val DISCOVER_GENRE = "with_genres"
    const val DISCOVER_SORT = "sort_by"
    const val IMAGE_POSTER_SIZE = "poster_sizes"
    const val LANGUAGE = "language"
    const val QUERY = "query"
    const val MOVIE_APPEND_TO_RESPONSE = "append_to_response"
    const val IMAGE_FILE_SIZE = "file_size"
    const val IMAGE_FILE_PATH = "file_path"

}

object ApiParameterValues {
    const val LANGUAGE_VALUE = "pt-BR"
    const val IMAGE_FEED_SIZE = "w300"
    const val IMAGE_DETAILS_SIZE = "w500"
}
