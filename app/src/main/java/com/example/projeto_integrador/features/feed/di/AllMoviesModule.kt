package com.example.projeto_integrador.features.feed.di

import com.example.projeto_integrador.common.data.MoviesRepositoryImpl
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.common.data.api.models.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.common.data.api.models.mappers.ApiMovieMapper
import com.example.projeto_integrador.common.data.di.ApiService
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.models.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.domain.usecases.RequestNextPageOfMoviesUseCase
import com.example.projeto_integrador.features.feed.presentation.AllMoviesViewModel
import com.example.projeto_integrador.features.feed.uttils.DispatchersProviderImp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal val allMoviesModule = module {

    //Service
    factory { ApiService(retrofit = get()).createService(TmdbApi::class.java)}

    factory<MoviesRepository> {
        MoviesRepositoryImpl(
            api = get(),
            apiDiscoverMapper =  ApiDiscoverMapper(
            apiMovieMapper = ApiMovieMapper()
            )
        )
    }

    //Use cases
    factory {
        RequestNextPageOfMoviesUseCase(get())
    }

    single { DispatchersProviderImp() }

    viewModel {
        AllMoviesViewModel(
            uiMovieMapper = UiMovieMapper(),
            requestNextPageOfMoviesUseCase = get(),
            dispatchersProvider = get()
        )
    }

}


internal val allMoviesDependencies by lazy {
    loadKoinModules(allMoviesModule)
}

fun initAllMoviesDependencies() = allMoviesDependencies


