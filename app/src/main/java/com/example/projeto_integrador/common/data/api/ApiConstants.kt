package com.example.projeto_integrador.data

object ApiConstants {

    const val BASE_ENDPOINT = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_ENDPOINT = "http://image.tmdb.org/t/p/"
    const val AUTH_ENDPOINT = "https://api.themoviedb.org/3/movie/76341"
    const val CONTENT_TYPE_ENDPOINT = "application/json;charset=utf-8"

    const val MOVIES_ENDPOINT = "movies"
    const val GENRE_ENDPOINT = "genre"
    const val SEARCH_ENDPOINT = "search"
    const val LANGUAGE_ENDPOINT = "language"
    const val DISCOVER_ENDPOINT = "discover"

}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer"
    const val AUTH_HEADER = "Authorization"
    const val CONTENT_TYPE_HEADER = "Content-Type"

    const val PAGE = "page"
    const val MOVIE_LIST = "list"
    const val POPULAR = "popular"
    const val IMAGES = "images"
    const val CREDITS = "credits"
    const val DISCOVER_GENRE = "with_genres"
    const val DISCOVER_SORT = "sort_by"
    const val IMAGE_POSTER_SIZE = "poster_sizes"

}
