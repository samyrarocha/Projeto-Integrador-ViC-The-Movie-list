package com.example.projeto_integrador.stubs

import com.example.projeto_integrador.data.cache.model.CachedMovie

fun moviesListStub() = listOf(
    CachedMovie(
        movieId = 505642,
        movieTitle = "Black Panther: Wakanda Forever",
        popularity = 3055.545F,
        posterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
        favorite = true
    ),
    CachedMovie(
        movieId = 646389,
        movieTitle = "Plane",
        popularity = 2766.635F,
        posterPath = "/qi9r5xBgcc9KTxlOLjssEbDgO0J.jpg",
        favorite = false
    )
)

fun firstMovieStub() = CachedMovie(
    movieId = 505642,
    movieTitle = "Black Panther: Wakanda Forever",
    popularity = 3055.545F,
    posterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
    favorite = true
)

fun secondMovieStub() = CachedMovie(
    movieId = 646389,
    movieTitle = "Plane",
    popularity = 2766.635F,
    posterPath = "/qi9r5xBgcc9KTxlOLjssEbDgO0J.jpg",
    favorite = false
)

fun changedSecondMovieStub() = CachedMovie(
    movieId = 646389,
    movieTitle = "Plane",
    popularity = 2766.635F,
    posterPath = "/qi9r5xBgcc9KTxlOLjssEbDgO0J.jpg",
    favorite = true
)

