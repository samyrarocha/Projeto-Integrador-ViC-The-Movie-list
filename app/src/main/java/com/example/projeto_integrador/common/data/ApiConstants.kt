package com.example.projeto_integrador.data

object ApiConstants {

    const val BASE_END_POINT = "https://api.themoviedb.org/3/"
  

    const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ODNjN2JmY2E2MmY4NTI5ODg3NDIyMmVkNTA0YWNmNSIsInN1YiI6IjYwZjQ3MjM3NTM4NjZlMDAyZDNmMWMxMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MpqkuQF6E5fDH46V1rSF7YHpQVDOt-wly1k2eFbA6tw"
    const val MOVIES_ENDPOINT = "movies"
    const val GENRE_ENDPOINT = "genre"
    const val SEARCH_ENDPOINT = "search"
    const val LANGUAGE_ENDPOINT = "language"
    const val DISCOVER_ENDPOINT = "discover"



}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer"
    const val AUTH_HEADER = "Authorization"

    const val PAGE = "page"
    const val MOVIE_LIST = "list"
    const val POPULAR = "popular"
    const val IMAGES = "images"
    const val CREDITS = "credits"
    const val DISCOVER_GENRE = "with_genres"
    const val DISCOVER_SORT = "sort_by"

}
