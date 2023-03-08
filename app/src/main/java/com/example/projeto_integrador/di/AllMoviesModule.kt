package com.example.projeto_integrador.di

import com.example.projeto_integrador.data.repository.GenreRepositoryImp
import com.example.projeto_integrador.data.repository.MoviesRepositoryImp
import com.example.projeto_integrador.data.api.models.TmdbApi
import com.example.projeto_integrador.domain.repository.GenreRepository
import com.example.projeto_integrador.domain.repository.MoviesRepository
import com.example.projeto_integrador.data.mappers.*
import com.example.projeto_integrador.domain.mappers.*
import com.example.projeto_integrador.domain.usecases.*
import com.example.projeto_integrador.presentation.feed.AllMoviesViewModel
import com.example.projeto_integrador.domain.uttils.DispatchersProviderImp
import com.example.projeto_integrador.presentation.moviedetails.MovieDetailsViewModel
import com.example.projeto_integrador.domain.usecases.GetMovieDetailsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal val allMoviesModule = module {

    //Service
    factory { ApiService(retrofit = get()).createService(TmdbApi::class.java)}

    factory<MoviesRepository> {
        MoviesRepositoryImp(
            api = get(),
            apiDiscoverMapper =  ApiDiscoverMapper(apiMovieMapper = ApiMovieMapper()),
            apiSearchMapper = ApiSearchMapper(apiSearchResultsMapper = ApiSearchResultsMapper()),
            apiMovieDetailsMapper = ApiMovieDetailsMapper(
                apiCreditsMapper = ApiCreditsMapper(
                    apiCastMapper = ApiCastMapper(),
                    apiCrewMapper = ApiCrewMapper()
                ),
            apiGenreMapper = ApiGenreMapper()
            ),
            cache = get()

        )
    }

    factory<GenreRepository> {
        GenreRepositoryImp(
            api = get(),
            apiGenreMapper = ApiGenreMapper()
        )
    }

    single { DispatchersProviderImp() }

    viewModel {
        AllMoviesViewModel(
            uiMovieMapper = UiMovieMapper(),
            uiGenreMapper = UiGenreMapper(),
            uiSearchResultsMapper = UiSearchResultsMapper(),
            searchMoviesUseCase = SearchMoviesUseCase(get()),
            requestNextPageOfMoviesUseCase = RequestNextPageOfMoviesUseCase(get()),
            genreListUseCase = GenreListUseCase(get()),
            getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(get()),
            getMoviesUseCase = GetMoviesUseCase(get()),
            updateFavoriteMovieUseCase =  UpdateFavoriteMovieUseCase(get())
        )
    }

    viewModel {
        MovieDetailsViewModel(
            uiMovieDetailsMapper = UiMovieDetailsMapper(
                UiGenreMapper(),
                UiCrewMapper(),
                UiCastMapper()
            ),
            dispatchersProvider = get(),
            getMovieDetailsUseCase = GetMovieDetailsUseCase(get())
        )
    }

}


internal val allMoviesDependencies by lazy {
    loadKoinModules(allMoviesModule)
}

fun initAllMoviesDependencies() = allMoviesDependencies


