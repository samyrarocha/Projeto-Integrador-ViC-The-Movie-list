package com.example.projeto_integrador.features.feed.di

import com.example.projeto_integrador.common.data.MoviesRepositoryImpl
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.domain.usecases.RequestNextPageOfMovies
import com.example.projeto_integrador.features.feed.presentation.AllMoviesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal val allMoviesModule = module {

    factory<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }

    //Use cases
    factory {
        RequestNextPageOfMovies(get())
        GetMovies(get())
    }

    viewModel {
        AllMoviesFragmentViewModel(
            uiMovieMapper = get(),
            requestNextPageOfMovies = get(),
            dispatchersProvider = get(),
            compositeDisposable = get()
        )
    }

}

internal val AllMoviesDependencies by lazy {
    loadKoinModules(allMoviesModule)
}

fun initAllMoviesDependencies() = allMoviesModule