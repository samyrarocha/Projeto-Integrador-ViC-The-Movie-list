package com.example.projeto_integrador.features.feed.di

import com.example.projeto_integrador.common.data.GenreRepositoryImp
import com.example.projeto_integrador.common.data.MoviesRepositoryImpl
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.common.data.api.models.mappers.*
import com.example.projeto_integrador.common.data.di.ApiService
import com.example.projeto_integrador.common.domain.repositories.GenreRepository
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiGenreMapper
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.domain.usecases.GenreListUseCase
import com.example.projeto_integrador.features.feed.domain.usecases.RequestNextPageOfMoviesUseCase
import com.example.projeto_integrador.features.feed.presentation.AllMoviesViewModel
import com.example.projeto_integrador.features.feed.uttils.DispatchersProviderImp
import com.example.projeto_integrador.features.moviedetails.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal val allMoviesModule = module {

    //Service
    factory { ApiService(retrofit = get()).createService(TmdbApi::class.java)}

    factory<MoviesRepository> {
        MoviesRepositoryImpl(
            api = get(),
            apiDiscoverMapper =  ApiDiscoverMapper(apiMovieMapper = ApiMovieMapper()),
            apiSearchMapper = ApiSearchMapper(apiSearchResultsMapper = ApiSearchResultsMapper())
        )
    }

    factory<GenreRepository> {
        GenreRepositoryImp(
            api = get(),
            apiGenreMapper = ApiGenreMapper()
        )
    }

    //Use cases
    factory {
        RequestNextPageOfMoviesUseCase(get())
    }

    factory {
        GenreListUseCase(get())
    }

    single { DispatchersProviderImp() }

    viewModel {
        AllMoviesViewModel(
            uiMovieMapper = UiMovieMapper(),
            uiGenreMapper = UiGenreMapper(),
            requestNextPageOfMoviesUseCase = get(),
            genreListUseCase = get(),
            dispatchersProvider = get()
        )
    }

    viewModel {
        MovieDetailsViewModel(
            moviesRepository = get(),
            dispatchersProvider = get()
        )
    }

}


internal val allMoviesDependencies by lazy {
    loadKoinModules(allMoviesModule)
}

fun initAllMoviesDependencies() = allMoviesDependencies


