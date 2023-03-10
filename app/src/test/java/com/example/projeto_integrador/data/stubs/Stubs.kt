package com.example.projeto_integrador.data.stubs

import com.example.projeto_integrador.data.api.models.*
import com.example.projeto_integrador.domain.model.movies.*
import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.domain.model.movies.details.credits.Credits
import com.example.projeto_integrador.domain.model.movies.details.credits.cast.Cast
import com.example.projeto_integrador.domain.model.movies.details.credits.crew.Crew

fun getApiDiscoverStub() = ApiDiscover(
    discoverPage = 1,
    discoverResults = getResultsForApiDiscover(),
    numberOfItems = 2,
    totalPages = 1
)

fun getResultsForApiDiscover() = listOf(
    ApiMovie(
        discoverPosterPath = "/dm06L9pxDOL9jNSK4Cb6y139rrG.jpg",
        discoverVoteAverage = 6.5F,
        discoverMovieTitle = "Knock at the Cabin",
        discoverMovieId = 631842
    ),
    ApiMovie(
        discoverPosterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
        discoverVoteAverage = 7.4F,
        discoverMovieTitle = "Black Panther: Wakanda Forever",
        discoverMovieId = 505642
    )
)

fun getDiscoverStub() = Discover(
    page = 1,
    movies = getResultsForDiscover(),
    numberOfItems = 2,
    totalPages = 1
)

fun getResultsForDiscover() = listOf(
    Movie(
        discoverMovieId = 631842,
        discoverMovieTitle = "Knock at the Cabin",
        discoverVoteAverage = 6.5F,
        favorite = false,
        discoverPosterPath = "/dm06L9pxDOL9jNSK4Cb6y139rrG.jpg"
    ),
    Movie(
        discoverPosterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
        discoverVoteAverage = 7.4F,
        discoverMovieTitle = "Black Panther: Wakanda Forever",
        discoverMovieId = 505642,
        favorite = false
    )
)

fun getResultsForApiSearch() = ApiSearch(
    searchPage = 1,
    searchResults = listOf(
        ApiSearchResults(
            searchPosterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
            searchVoteAverage = 7.4F,
            searchMovieTitle = "Black Panther: Wakanda Forever",
            searchMovieId = 505642,
            searchGenresId = listOf(16, 10751, 12, 14)
        )
    ),
    totalPages = 1,
    totalResults = 1
)

fun getResultsForSearch() = Search(
    searchPage = 1,
    searchResults = listOf(
        SearchResults(
            searchPosterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
            searchVoteAverage = 7.4F,
            searchMovieTitle = "Black Panther: Wakanda Forever",
            searchMovieId = 505642,
        )
    ),
    searchTotalPages = 1,
    searchTotalResults = 1
)

fun getApiMovieDetails() = ApiMovieDetails(
    detailsMovieId = 550,
    detailsGenres = getGenreListForApiMovieDetails(),
    detailsPosterPath = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
    detailsTitle = "Fight Club",
    detailsRuntime = 139,
    detailsVoteAverage = 8.431F,
    overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
    releaseDate = "1999-10-15",
    credits = getCreditsForApiMovieDetails()
)

fun getGenreListForApiMovieDetails() = listOf(
    ApiGenre(
        genreId = 18,
        genreName = "Drama"
    ),
    ApiGenre(
        genreId = 53,
        genreName = "Thriller"
    ),
    ApiGenre(
        genreId = 35,
        genreName = "Comedy"
    )
)

fun getCreditsForApiMovieDetails() = ApiCredits(
    creditCast = listOf(
        ApiCast(
            castId = 819,
            castCharacter = "The Narrator",
            castPosterPath = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg",
            castName = "Edward Norton",
        )
    ),
    creditCrew = listOf(
        ApiCrew(
            crewId = 7467,
            crewPosterPath = "/tpEczFclQZeKAiCeKZZ0adRvtfz.jpg",
            crewName = "David Fincher",
            crewJob = "Director"
        )
    )
)

fun getMovieDetails() = MovieDetails(
    detailsGenreName = getGenreList(),
    detailsBackDropPath = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
    detailsNameTitle = "Fight Club",
    detailsRuntime = 139,
    detailsVoteAverage = 8.431F,
    detailsOverview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
    detailsReleasedYear = "1999-10-15",
    credits = getCreditsForMovieDetails()
)

fun getGenreList() = listOf(
    Genre(
        genreId = 18,
        genreName = "Drama"
    ),
    Genre(
        genreId = 53,
        genreName = "Thriller"
    ),
    Genre(
        genreId = 35,
        genreName = "Comedy"
    )
)

fun getCreditsForMovieDetails() = Credits(
    creditCast = listOf(
        Cast(
            castId = 819,
            castCharacter = "The Narrator",
            castPosterPath = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg",
            castName = "Edward Norton",
        )
    ),
    creditCrew = listOf(
        Crew(
            crewId = 7467,
            crewPosterPath = "/tpEczFclQZeKAiCeKZZ0adRvtfz.jpg",
            crewName = "David Fincher",
            crewJob = "Director"
        )
    )
)

fun getFavoriteMovies() = listOf(
    Movie(
        discoverMovieId = 631842,
        discoverMovieTitle = "Knock at the Cabin",
        discoverVoteAverage = 6.5F,
        favorite = true,
        discoverPosterPath = "/dm06L9pxDOL9jNSK4Cb6y139rrG.jpg"
    ),
    Movie(
        discoverPosterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
        discoverVoteAverage = 7.4F,
        discoverMovieTitle = "Black Panther: Wakanda Forever",
        discoverMovieId = 505642,
        favorite = true
    )
)